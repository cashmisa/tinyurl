<template>
  <q-layout view="lHh Lpr lFf" class="bg-grey-3">
    <q-header elevated>
      <q-toolbar class="bg-primary">
        <q-avatar color="secondary" text-color="primary" size="26px"><strong>T</strong></q-avatar>
        <q-toolbar-title>
          TinyUrl
        </q-toolbar-title>
      </q-toolbar>
    </q-header>
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>

import { defineComponent, ref } from 'vue'

export default defineComponent({
  name: 'MainLayout',

  components: {
  },

  setup () {

    return {

    }
  }
})
</script>
