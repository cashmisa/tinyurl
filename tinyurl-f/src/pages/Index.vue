<template>
  <q-page padding class="main-background flex flex-center column">
    <q-btn-toggle
      v-model="reversal"
      toggle-color="accent"
      rounded
      :options="[
        { label: 'shorten', value: false },
        { label: 'turn back', value: true },
      ]"
    />
    <h4>
      {{
        reversal
          ? "Enter the shortened URL to turn it back"
          : "Enter a long URL to shorten"
      }}
    </h4>
    <div class="full-width row wrap justify-center">
      <q-input
        ref="urlRef"
        rounded
        clearable
        class="col-grow self-center q-mr-md"
        :hint="
          reversal
            ? 'starts with http://localhost:8080/qwe123'
            : 'e.g. https://example.com/loooooooooong'
        "
        standout="bg-grey-5 text-black"
        type="url"
        v-model="url"
        lazy-rules
        :rules="[isValidUrl || 'Must be a valid URL']"
        @keyup.enter="convertUrl"
        @update:model-value="reset"
      ></q-input>
      <div class="col-shrink">
        <q-btn
          :color="reversal ? 'warning' : 'secondary'"
          size="lg"
          rounded
          :label="reversal ? 'Original' : 'Shorten'"
          @click="convertUrl"
        ></q-btn>
      </div>
    </div>
    <div class="self-start q-mt-md">
      <q-btn
        v-if="requestedUrls.length > 0"
        flat
        size="sm"
        @click="clearHistory"
        >Clear history</q-btn
      >
    </div>
    <q-list
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
        <!-- <q-item-section side>
          <q-btn name="copy" flat color="primary" @click="copy(u.convertedUrl)"
            >copy</q-btn
          >
        </q-item-section> -->
        <q-item-section top side>
          <div class="text-grey-8 q-gutter-xs">
            <q-btn class="gt-xs" size="12px" flat dense round icon="content_copy"  color="primary" @click="copy(u.convertedUrl)">
              <q-tooltip>copy to clipboard</q-tooltip>
            </q-btn>
            <q-btn class="gt-xs" size="12px" flat dense round icon="open_in_new" color="primary" @click="openURL(u.convertedUrl)">
              <q-tooltip>open in new tab</q-tooltip>
            </q-btn>
          </div>
        </q-item-section>
      </q-item>
    </q-list>
  </q-page>
</template>

<script>
import { defineComponent, ref } from "vue";
import { useQuasar } from "quasar";
import { post } from "axios";
import { copyToClipboard, openURL } from "quasar";

export default defineComponent({
  name: "PageIndex",
  setup() {
    const $q = useQuasar();
    const reversal = ref(false);
    const url = ref(null);
    const urlRef = ref(null);
    const requestedUrls = ref([]);

    async function convertUrl() {
      if (!url.value) {
        $q.notify({
          type: "negative",
          message: "Oops, there is no URL to convert",
        });
        return
      }
      urlRef.value.validate();
      if (urlRef.value.hasError) {
        $q.notify({
          type: "negative",
          message: "Please check your URL before submit",
        });
      } else {
        let res;
        if (reversal.value) {
          res = await post("/turnback", { url: url.value});
        } else {
          res = await post("/shorten", { url: url.value});
        }
        await copy(res.data);
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
      if (!val) return true
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
      openURL
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
