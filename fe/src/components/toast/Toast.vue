<template>
  <div class="toast-box" v-show="isShow">
    <div>{{content}}</div>
  </div>
</template>

<script>
export default {
  name: "Toast",
  data() {
    return {
      isShow: false,
      content: "",
      times: null
    };
  },
  methods: {
    show(text = "操作成功", duraction = 3) {
      this.content = text;
      this.isShow = true;
      if (this.times) clearTimeout(this.times);
      this.times = setTimeout(() => {
        this.content = "";
        this.isShow = false;
      }, duraction * 1000);
    }
  }
};
</script>

<style lang="scss" scoped>
.toast-box {
  position: fixed;
  left: 50%;
  top: 75%;
  transform: translate(-50%, -50%);
  z-index: 99999;

  max-width: 60%;
  height: auto;
  padding: 2rem 2rem;
  box-sizing: border-box;
  border-radius: 0.5rem;
  background-color: rgba(0, 0, 0, 0.5);
  color: #fff;
}
</style>