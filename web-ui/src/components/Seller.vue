<template>
  <div class="seller">
    <h3>{{ message }}</h3>
    <div>...spoiler...</div>
    <button v-on:click="getHome">Reverse Message</button>

  </div>
</template>


<script>
  const axios = require('axios');

  export default {
    name: 'Seller',
    props: {
      msg: String
    },
    data: function () {
      return {
        message: this.msg
      }
    },
    methods: {
      async getHome() {
        // Make a request for a user with a given ID
        this.message = await axios.get('http://localhost:8888/')
        .then(function (response) {
          console.log(response);
          return response.data
        })
        .catch(function (error) {
          console.log(error);
          return "error occured"
        });
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  body {
    background: red;
  }
</style>