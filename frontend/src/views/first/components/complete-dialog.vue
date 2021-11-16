<template lang="">
  <q-dialog>
    <q-card>
      <q-card-section>
        <div>🎉회원가입이 완료되었습니다🎉</div>
        <div>CS에 오신것을 환영합니다</div>
        <div class="finish-regist">
          <q-btn
            rounded
            style="background: #495057; color: #ffffff"
            @click="mvLogin"
            >로그인하기</q-btn
          >
        </div>
      </q-card-section>
    </q-card>
  </q-dialog>
</template>
<script>
export default {
  name: "register-complete-dialog",
  setup(props, { emit }) {
    const mvLogin = () => {
      emit("mvlogin");
    };
    return {
      mvLogin,
    };
  },
};
</script>
<style lang=""></style>
