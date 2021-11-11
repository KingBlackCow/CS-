import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./lib/store";
import { Quasar, Loading, Notify } from "quasar";
import quasarUserOptions from "./quasar-user-options";
import VueApexCharts from "vue3-apexcharts";
import Vue3Autocounter from "vue3-autocounter";
import AOS from "aos";
import "aos/dist/aos.css";
import "fullpage.js/vendors/scrolloverflow"; // Optional. When using scrollOverflow:true
import VueFullPage from "vue-fullpage.js";

createApp(App)
  .use(Quasar, {
    plugins: {
      Loading,
      quasarUserOptions,
      Notify,
    },
    config: {
      loading: {
        /* look at QuasarConfOptions from the API card */
      },
      notify: {
        /* look at QuasarConfOptions from the API card */
      },
    },
  })
  .use(store)
  .use(router)
  .use(VueApexCharts)
  .use(AOS.init())
  .use("vue3-autocounter", Vue3Autocounter)
  .use(VueFullPage)
  .mount("#app");
