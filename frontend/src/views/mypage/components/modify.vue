<template>
  <div class="modify-wrap">
    <span class="modify-title">프로필 수정하기</span>
    <div class="form-wrap">
      <div class="modify-image">
        <img class="modi-img" :src="state.imageurl" />
        <label for="chooseFile" class="upload-btn">사진변경</label>
        <input
          type="file"
          id="chooseFile"
          name="chooseFile"
          accept="image/*"
          style="display: none"
          @change="loadf"
        />
      </div>
    </div>
    <div class="submit-wrap">
      <q-btn class="subbtn" type="submit" @click="modify" label="수정하기" />
    </div>
  </div>
</template>
<script>
import { reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  name: "modify",
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      user: null,
      imageurl: null,
      file: null,
    });

    state.user = JSON.parse(localStorage.getItem("userInfo"));
    state.imageurl = state.user.image;

    /*ㅡㅡㅡㅡㅡ 버튼 ㅡㅡㅡㅡㅡ*/
    const loadf = () => {
      var file = document.getElementById("chooseFile");
      state.imageurl = URL.createObjectURL(file.files[0]);
    };

    const modify = () => {
      const formData = new FormData();
      const img = document.getElementById("chooseFile").files[0];
      formData.append("email", state.user.email);
      if (img == null) {
        alert("이미지가 첨부되지 않아 기본이미지로 변경합니다");
      } else {
        formData.append("image", img);
      }
      store
        .dispatch("root/requestUserModify", formData)
        .then((response) => {
          if (response.data.message === "Success") {
            alert("변경이 완료되었습니다.");
            store
              .dispatch("root/requsetUserInfo", state.user.token)
              .then((response) => {
                var userinfo = {
                  id: response.data.id,
                  name: response.data.name,
                  nickname: response.data.nickname,
                  email: response.data.email,
                  image: response.data.image,
                  token: state.user.token,
                };
                localStorage.removeItem("userInfo");
                localStorage.setItem("userInfo", JSON.stringify(userinfo));
                state.user = JSON.parse(localStorage.getItem("userInfo"));
                router.go();
              })
              .catch((error) => {
                console.log(error);
              });
          }
          // router.push({ name: "mypage-chart" });
        })
        .catch((error) => {
          console.log(error);
        });
    };

    return {
      state,
      loadf,
      modify,
    };
  },
};
</script>
