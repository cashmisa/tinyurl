<template>
  <q-page padding class="main-background flex flex-center column">
    <q-toggle
      v-model="reversal"
      size="xl"
      checked-icon="undo"
      color="red"
      :label="
        reversal
          ? 'I want to shorten a long url'
          : 'I want to bring back the long url'
      "
      unchecked-icon="redo"
    />
    <h4>
      {{
        reversal
          ? "Enter the shortened Url to turn it back"
          : "Enter a long URL to shorten"
      }}
    </h4>
    <div class="full-width row wrap justify-center">
      <q-input
        ref="urlRef"
        rounded
        class="col-grow self-center q-mr-md"
        :placeholder="
          reversal
            ? 'http://localhost:8080/qwe123'
            : 'https://example.com/loooooooooong'
        "
        standout="bg-grey-5 text-black"
        type="url"
        v-model="url"
        lazy-rules
        :rules="[isValidUrl || 'Must be a valid url']"
        @update:model-value="reset"
      ></q-input>
      <div class="col-shrink">
        <q-btn
          color="secondary"
          size="lg"
          rounded
          :label="reversal ? 'Original' : 'Shorten'"
          @click="convertUrl"
        ></q-btn>
      </div>
    </div>
    <div class="self-end q-mt-md">
      <q-btn v-if="requestedUrls.length > 0" flat size="sm" @click="clearHistory">Clear history</q-btn>
    </div>
    <q-list
      bordered
      separator
      class="bg-grey-5 full-width"
      v-if="requestedUrls.length > 0"
    >
      <q-item
        v-for="u in requestedUrls"
        :key="u.convertedUrl"
        class="row justify-between"
      >
        <q-item-section style="max-width: 500px">
          <q-item-label lines="5">{{ u.convertedUrl }}</q-item-label>
          <q-item-label caption lines="1">{{ u.originalUrl }}</q-item-label>
        </q-item-section>
        <q-item-section side>
          <q-btn name="copy" flat color="primary" @click="copy(u.convertedUrl)"
            >copy</q-btn
          >
        </q-item-section>
      </q-item>
    </q-list>
  </q-page>
</template>

<script>
import { defineComponent, ref } from "vue";
import { useQuasar } from "quasar";
import { post } from "axios";
import { copyToClipboard } from "quasar";

export default defineComponent({
  name: "PageIndex",
  setup() {
    const $q = useQuasar();
    const reversal = ref(false);
    const url = ref(null);
    const urlRef = ref(null);
    const requestedUrls = ref([]);

    async function convertUrl() {
      urlRef.value.validate();
      if (urlRef.value.hasError) {
        $q.notify({
          type: "negative",
          message: "Please check your url before submit",
        });
      } else {
        let res;
        if (reversal.value) {
          res = await post("/api/expand", encodeURI(url.value));
        } else {
          res = await post("/api/shrink", encodeURI(url.value));
        }
        await copy(res.data);
        requestedUrls.value.forEach((u) => (u.copied = false));
        requestedUrls.value.unshift({
          originalUrl: url.value,
          convertedUrl: res.data,
        });
        url.value = null;
      }
    }

    async function copy(str) {
      try {
        await copyToClipboard(str);
        $q.notify({
          type: "positive",
          message: "copied to clipboard",
          timeout: 500,
        });
      } catch (err) {}
    }

    function isValidUrl(val) {
      let link;
      try {
        link = new URL(val);
      } catch (_) {
        return false;
      }
      return link.protocol === "http:" || link.protocol === "https:";
    }

    function clearHistory() {
      requestedUrls.value.length = 0;
    }

    return {
      reversal,
      url,
      urlRef,
      isValidUrl,
      reset() {
        urlRef.value.resetValidation();
      },
      copy,
      requestedUrls,
      convertUrl,
      clearHistory,
    };
  },
});
</script>
<style scoped>
.main-background {
  background: url("../../public/background.jpg") no-repeat center center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
</style>
