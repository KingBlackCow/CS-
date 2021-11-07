<template>
  <div class="right-wrap">
    <div class="inner-wrap">
      <div class="contentWrap">
        <div>
          <img
            :src="require('@/assets/fin0.jpg')"
            class="img0"
            alt="이미지1"
            @click="visible(0)"
          />
          <img
            :src="require('@/assets/fin1.jpg')"
            class="img1"
            alt="이미지2"
            @click="visible(1)"
          />
        </div>
        <div>
          <img
            :src="require('@/assets/fin2.jpg')"
            class="img2"
            alt="이미지3"
            @click="visible(2)"
          />
          <img
            :src="require('@/assets/fin3.jpg')"
            class="img3"
            alt="이미지4"
            @click="visible(3)"
          />
        </div>
      </div>
      <div class="buttonWrap">
        <button type="button" @click="addAction">요소 나타내기</button>
        <button type="button" @click="removeAction">요소 없애기</button>
        <button type="button" @click="prevAction">되돌아가기</button>
      </div>
    </div>
  </div>
</template>
<script>
import "@/styles/info.scss";
import { onMounted } from "vue";
export default {
  name: "info-right",
  setup() {
    let contentWrap;
    let infoArr;
    onMounted(() => {
      contentWrap = document.querySelector(".contentWrap");
      infoArr = document.querySelectorAll("img");
      console.log(contentWrap);
      pageSet();
    });
    const pageSet = () => {
      removeAction();
      addAction();
    };

    const visible = (index) => {
      console.log(index);
      removeAction();
      for (var i = 0; i < infoArr.length; i++) {
        if (index != i) {
          infoArr[i].classList.add("invisible");
        }
      }
      infoArr[index].classList.add("visible");
    };
    const addAction = () => {
      console.log("addAction");
      contentWrap.classList.add("active");
      for (var i = 0; i < infoArr.length; i++) {
        infoArr[i].classList.add("active");
      }
    };
    const removeAction = () => {
      console.log("removeAction");
      contentWrap.classList.remove("active");
      for (var i = 0; i < infoArr.length; i++) {
        infoArr[i].classList.remove("active");
      }
    };
    const prevAction = () => {
      console.log("prevAction");
      for (var i = 0; i < infoArr.length; i++) {
        infoArr[i].classList.remove("visible");
      }
      for (i = 0; i < infoArr.length; i++) {
        infoArr[i].classList.remove("invisible");
      }
      addAction();
    };

    return {
      contentWrap,
      pageSet,
      visible,
      addAction,
      removeAction,
      prevAction,
    };
  },
};
</script>
