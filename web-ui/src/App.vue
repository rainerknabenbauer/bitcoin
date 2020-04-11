<template>
  <div id="app">
    <Seller msg="Welcome to Your Vue.js App"/>
  </div>
</template>

<script>
import Seller from './components/Seller.vue'

export default {
  name: 'App',
  components: {
    Seller
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
