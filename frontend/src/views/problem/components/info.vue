<template lang="">
  <div class="problem-right-wrap">
    <h3 data-aos="fade-up"><b>왼쪽 카테고리에서 문제를 선택해주세요.</b></h3>
    <h4 data-aos="fade-left">
      <b>전체 카테고리 : {{ state.categories.length }}</b>
    </h4>
    <h4 data-aos="fade-right">
      <b>CSPS의 총 문제 개수 : {{ state.problemNum }}</b>
    </h4>
    <q-btn
      style="background: #696969; color: #ffffff"
      @click="makeprob"
      label="문제만들기"
    />
  </div>
</template>
<script>
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { computed, reactive } from "vue";

export default {
  name: "login-nav",
  data() {
    return {};
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      categories: computed(() => store.getters["root/getCategories"]),
      problemNum: computed(() => store.getters["root/getProblemNum"]),
    });

    const makeprob = () => {
      router.push({ name: "problem-make" });
    };
    return {
      state,
      makeprob,
    };
  },
  methods: {
    check() {
      console.log(this.state.problemNum);
    },
  },
};
</script>
<style lang=""></style>
