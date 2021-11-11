<template lang="">
  <div class="right-wrap">
    <div class="inner-wrap login-wrap">
      <q-form
        class="q-gutter-md login-form"
        ref="login_form"
        @submit="onSubmit"
        @reset="onReset"
      >
        <q-input
          ref="email"
          v-model="state.form.email"
          :rules="state.rules.email"
          lazy-rules
          type="email"
          label="이메일"
        >
        </q-input>
        <q-input
          dense
          v-model="state.form.pass"
          :rules="state.rules.pass"
          lazy-rules
          type="password"
          label="비밀번호"
          @keyup.enter="
            onSubmit;
            $event.target.blur();
          "
        >
        </q-input>
        <div>
          <q-btn
            color="primary"
            type="submit"
            :disable="state.login_btn_disable"
            label="로그인"
          />
          <q-btn
            class="q-ml-sm"
            label="비밀번호 찾기"
            type="reset"
            color="primary"
            flat
            @click="state.dialog.findpw = true"
          />
        </div>
      </q-form>
      <find-pw-dialog
        v-model="state.dialog.findpw"
        @openemailcheck="openEmailCheckDialog"
      ></find-pw-dialog>
      <email-check-dialog
        v-model="state.dialog.emailcheck"
        :email="state.emailcheck.email"
        :authNumber="state.emailcheck.authNumber"
        @openupdatepw="openUpdatePwDialog"
      ></email-check-dialog>
      <update-pw-dialog
        v-model="state.dialog.updatepw"
        :email="state.emailcheck.email"
        @mvlogin="mvLogin"
      ></update-pw-dialog>
      <!-- <q-btn round size="sm" color="accent" @click="showNotif('center')">
        <q-icon name="fullscreen_exit" />
      </q-btn> -->
      <q-btn label="Alert" color="primary" @click="state.alert = true" />
      <q-dialog v-model="alert">
        <q-card>
          <q-card-section>
            <div class="text-h6">Alert</div>
          </q-card-section>

          <q-card-section class="q-pt-none">
            비밀번호가 다릅니다.
          </q-card-section>

          <q-card-actions align="right">
            <q-btn flat label="OK" color="primary" v-close-popup />
          </q-card-actions>
        </q-card>
      </q-dialog>
    </div>
  </div>
</template>
<script>
import { ref, reactive, watch, onBeforeUnmount } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import FindPwDialog from "./components/findpw.vue";
import EmailCheckDialog from "./components/emailcheck.vue";
import UpdatePwDialog from "./components/updatepw.vue";
import { useQuasar } from "quasar";
import "../../styles/register.scss";

export default {
  name: "login-right",
  components: {
    FindPwDialog,
    EmailCheckDialog,
    UpdatePwDialog,
  },
  setup() {
    const $q = useQuasar();

    let timer;
    onBeforeUnmount(() => {
      if (timer !== void 0) {
        clearTimeout(timer);
        $q.loading.hide();
      }
    });
    const alerts = [
      { message: "존재하지 않는 이메일입니다.", icon: "thumb_up" },
      {
        color: "teal",
        message: "비밀번호를 잘못입력하셨습니다.",
        icon: "tag_faces",
      },
    ];

    const login_form = ref(null); // 로그인폼저장
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      alert: ref(false),
      form: {
        email: "",
        pass: "",
      },
      rules: {
        /*
        즉변할때
        error-message="Please use maximum 3 characters"
        :error="!isValid"
        return -> isValid: computed(() => model.value.length <= 3)
        */
        email: [
          (val) => (val != null && val !== "") || "필수입력 항목입니다.",
          (val) => isValidEmail(val) || "이메일형식에 맞지 않습니다.",
        ],
        pass: [(val) => (val != null && val !== "") || "필수입력 항목입니다."],
      },
      login_btn_disable: true, // 버튼 활성, 비활성화
      dialog: {
        findpw: false,
        updatepw: false,
        emailcheck: false,
      },
      emailcheck: {
        email: "",
        authNumber: "",
      },
    });
    /*ㅡㅡㅡㅡㅡ 검증 ㅡㅡㅡㅡㅡ*/
    const isValidEmail = (val) => {
      // eslint-disable-next-line
      const emailPattern = /^[a-zA-Z0-9]+@[a-zA-Z]+\.[a-zA-Z]{2,4}$/;
      return emailPattern.test(val) || "이메일 형식에 맞지 않습니다.";
    };

    watch(
      () => [state.form.email, state.form.pass],
      () => {
        login_form.value.validate().then((success) => {
          if (success) {
            state.login_btn_disable = false;
          } else {
            state.login_btn_disable = true;
          }
        });
      }
    );
    /*ㅡㅡㅡㅡㅡ 버튼 ㅡㅡㅡㅡㅡ*/
    const onSubmit = () => {
      showLoading();
      login_form.value.validate().then((success) => {
        if (success) {
          store
            .dispatch("root/requestUserLogin", {
              email: state.form.email,
              pass: state.form.pass,
            })
            .then((response) => {
              $q.loading.hide();
              getUserInfo(response.data.token);
              localStorage.setItem("token", response.data.token);
              saveProblemCategory();
            })
            .catch((error) => {
              $q.loading.hide();
              state.alert = ref(true);
              alert(error.response.data.message);
            });
        } else {
          /* Error 모달 모음 만들기 */
        }
      });
    };
    const getUserInfo = (jwt_token) => {
      localStorage.setItem("token", jwt_token);
      store
        .dispatch("root/requsetUserInfo", jwt_token)
        .then(
          (response) => {
            var userinfo = {
              id: response.data.id,
              name: response.data.name,
              nickname: response.data.nickname,
              email: response.data.email,
              image: response.data.image,
              token: jwt_token,
            };

            store.commit("root/setUser", userinfo);
            localStorage.setItem("userInfo", JSON.stringify(userinfo));
            if (userinfo.email == "admin@naver.com") {
              router.push({ name: "approve" });
            } else {
              router.push({ name: "login-info" });
            }
          },
          (error) => {
            console.log(error);
          }
        )
        .catch((error) => {
          console.log(error);
        });
    };

    const onReset = () => {
      state.form.email = null;
      state.form.pass = null;
    };
    /*ㅡㅡㅡㅡㅡ 비밀번호 찾기 모달 제어 ㅡㅡㅡㅡㅡ*/
    const openEmailCheckDialog = (data) => {
      state.dialog.findpw = false;
      state.emailcheck = data;
      state.dialog.emailcheck = true;
    };
    const openUpdatePwDialog = () => {
      state.dialog.emailcheck = false;
      state.dialog.updatepw = true;
    };
    const mvLogin = () => {
      state.dialog.updatepw = false;
      router.push({ name: "login" });
    };

    const saveProblemCategory = () => {
      store
        .dispatch("root/requsetCategoryList")
        .then((response) => {
          store.commit("root/setCategories", response.data);
        })
        .catch((error) => {
          console.log(error);
        });
    };

    const showLoading = () => {
      $q.loading.show({
        message: "로그인 중입니다",
        boxClass: "bg-grey-2 text-grey-9",
        spinnerColor: "primary",
      });
    };

    return {
      login_form,
      state,
      /* 제출 */
      onSubmit,
      onReset,
      /* 모달 */
      openEmailCheckDialog,
      openUpdatePwDialog,
      mvLogin,
      showLoading,
      alert: ref(false),
      showNotif(position) {
        const { color, textColor, multiLine, icon, message, avatar } =
          alerts[Math.floor(Math.random(alerts.length) * 10) % alerts.length];
        const random = Math.random() * 100;
        const twoActions = random > 70;
        const buttonColor = color ? "white" : void 0;
        $q.notify({
          color,
          textColor,
          icon: random > 30 ? icon : null,
          message,
          position,
          avatar,
          multiLine,
          actions: twoActions
            ? [
                {
                  label: "Reply",
                  color: buttonColor,
                  handler: () => {
                    /* console.log('wooow') */
                  },
                },
                {
                  label: "Dismiss",
                  color: "yellow",
                  handler: () => {
                    /* console.log('wooow') */
                  },
                },
              ]
            : random > 40
            ? [
                {
                  label: "Reply",
                  color: buttonColor,
                  handler: () => {
                    /* console.log('wooow') */
                  },
                },
              ]
            : null,
          timeout: Math.random() * 5000 + 3000,
        });
      },
    };
  },
};
</script>
<style lang=""></style>
