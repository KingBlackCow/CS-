<template lang="">
  <q-dialog class="bookstyle-dialog">
    <div class="book">
      <div class="left-card-top row justify-end">
        <div class="left-card-top-left"></div>
        <div class="left-card-top-right"></div>
      </div>
      <div class="left-card-back">
        <div class="left-card">
          <div class="findpw-card-title">비밀번호찾기</div>
          <div class="findpw-card-info">
            본인의<br />
            이메일을<br />
            적어주세요
          </div>
        </div>
      </div>
      <div class="left-card-bottom-back row justify-end">
        <div class="left-card-bottom row">
          <div class="left-card-bottom-left"></div>
          <div class="left-card-bottom-right"></div>
        </div>
      </div>
    </div>
    <div class="book">
      <div class="right-card-top row">
        <div class="right-card-top-left"></div>
        <div class="right-card-top-right"></div>
      </div>
      <div class="right-card-back">
        <div class="right-card column">
          <q-btn
            class="findpw-card-close-btn self-end"
            v-close-popup
            flat
            round
            dense
            icon="close"
          />
          <q-form ref="email_form" @submit="emailAuthentication">
            <q-input
              class="findpw-card-email"
              dense
              v-model="state.email"
              :rules="state.rules.email"
              lazy-rules
              type="email"
              label="이메일"
            />
            <div>
              <q-btn
                class="findpw-card-btn"
                outline
                label="인증요청"
                type="submit"
              ></q-btn>
            </div>
          </q-form>
        </div>
      </div>
      <div class="right-card-bottom-back row">
        <div class="right-card-bottom row">
          <div class="right-card-bottom-left"></div>
          <div class="right-card-bottom-right"></div>
        </div>
      </div>
    </div>
  </q-dialog>
</template>
<script>
import "@/styles/bookdialog.scss";
import "@/styles/findpw.scss";
import { reactive } from "vue";
import { useStore } from "vuex";

export default {
  name: "login-findpw",
  setup(props, { emit }) {
    const store = useStore();
    // const email_form = ref(null);

    const state = reactive({
      email: "",
      rules: {
        email: [
          (val) => (val != null && val !== "") || "필수입력 항목입니다.",
          (val) => isValidEmail(val) || "이메일형식에 맞지 않습니다.",
        ],
      },
    });
    const isValidEmail = (val) => {
      // eslint-disable-next-line
      const emailPattern = /^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-zA-Z]{2,4}$/;
      return emailPattern.test(val) || "이메일 형식에 맞지 않습니다.";
    };

    const emailAuthentication = () => {
      store
        .dispatch("root/requestUserSendEmail", {
          email: state.email,
        })
        .then(
          (response) => {
            console.log(response.data);
            requsetAuth(response.data);
          },
          (error) => {
            alert("error:", error);
          }
        )
        .catch((error) => {
          console.log(error);
        });
    };
    const requsetAuth = (authNumber) => {
      console.log(state.email);
      emit("openemailcheck", {
        email: state.email,
        authNumber: authNumber.message,
      });
    };
    return {
      state,
      emailAuthentication,
      requsetAuth,
    };
  },
};
</script>
<style lang=""></style>
