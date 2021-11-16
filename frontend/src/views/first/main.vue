<template>
  <div class="first-wrap">
    <div class="first-left"></div>
    <div class="first-right">
      <span class="first-title">CS의 정석</span>
      <div class="first-buttons">
        <div class="login-btn" @click="showLogin">로그인</div>
        <div class="regist-btn" @click="showRegist">회원가입</div>
      </div>
    </div>
    <loginmodal v-model="state.loginmodal"></loginmodal>
    <registmodal v-model="state.registmodal"></registmodal>
  </div>
</template>
<script>
import "../../styles/first.scss";

import { reactive } from "vue";
import loginmodal from "./components/loginmodal.vue";
import registmodal from "./components/registmodal.vue";

export default {
  components: {
    loginmodal,
    registmodal,
  },

  setup() {
    const state = reactive({
      loginmodal: false,
      registmodal: false,
    });

    const showLogin = () => {
      state.loginmodal = true;
    };

    const showRegist = () => {
      state.registmodal = true;
    };

    return {
      state,
      showLogin,
      showRegist,
    };
  },
};
</script>
