<template lang="">
  <q-dialog class="bookstyle-dialog">
    <div class="book">
      <div class="left-card-top row justify-end">
        <div class="left-card-top-left"></div>
        <div class="left-card-top-right"></div>
      </div>
      <div class="left-card-back">
        <div class="left-card">
          <div class="emailcheck-card-title">비밀번호찾기</div>
          <div class="emailcheck-card-left-info">
            <div>
              {{ email }}로<br />
              인증번호를 발송했습니다.
            </div>
            <div>
              최소1분~최대5분의<br />
              소요시간이 있으니 기다려주세요
            </div>
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
            class="emailcheck-card-close-btn self-end"
            v-close-popup
            flat
            round
            dense
            icon="close"
          />
          <div class="emailcheck-card-right-info">
            <div class="emailcheck-card-right-info-top">
              <div>인증번호는 "몇분"뒤 폐기됩니다.</div>
              <div>다시 요청하려면<br />아래버튼을 눌러주세요</div>
            </div>
            <div class="emailcheck-card-right-info-bottom">
              <q-input dense v-model="state.auth" label="인증번호 *"> </q-input>
              <div>
                <q-btn
                  class="emailcheck-card-btn"
                  outline
                  @click="emailAuthentication"
                  >재요청</q-btn
                >
                <q-btn class="primary" outline @click="authentication"
                  >인증</q-btn
                >
              </div>
            </div>
          </div>
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
import "@/styles/logindialog.scss";
import "@/styles/emailcheck.scss";
import { reactive } from "vue";
import { useQuasar } from "quasar";
export default {
  name: "login-emailcheck",
  props: {
    email: String,
    authNumber: String,
  },
  setup(props, { emit }) {
    const quasar = useQuasar();
    const state = reactive({
      auth: "",
    });
    const emailAuthentication = () => {
      console.log("emailAuthentication");
    };
    const authentication = () => {
      console.log(props.authNumber, state.auth);
      if (props.authNumber === state.auth) {
        emit("openupdatepw");
      } else {
        authFail();
      }
    };
    /*ㅡㅡㅡㅡㅡ 다이얼로그 ㅡㅡㅡㅡㅡ*/
    const authFail = () => {
      quasar
        .dialog({
          title: "이메일 인증",
          message: "인증번호가 올바르지 않습니다.",
        })
        .onOk(() => {
          console.log("OK");
        })
        .onCancel(() => {
          console.log("Cancel");
        })
        .onDismiss(() => {
          console.log("I am triggered on both OK and Cancel");
        });
    };
    return {
      state,
      emailAuthentication,
      authentication,
    };
  },
};
</script>
<style lang=""></style>
