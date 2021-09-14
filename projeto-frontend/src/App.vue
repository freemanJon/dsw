<template>
  <div id="app" style="margin-top:0px">
    <nav class="navbar navbar-inverse navbar-fixed-top" style="height:80px">
      <div class="navbar-header" style="margin-right:1200px;margin-top:40px">
        <span class="navbar-brand" href="#">Shared4U</span>
      </div>

      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav navbar-left">
            <li v-if="$root.credentials">
              <router-link class="link" :to="{ name: 'item-list' }">Itens</router-link>
            </li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
          <li class="user-commands" v-if="$root.credentials">
            <span class="greetings">Olá {{$root.credentials.nome}}.</span> |
            <router-link class="link" :to="{ name: 'change-password' }" replace>Troca senha</router-link> |
            <a class="link" @click="logout">Logout</a>
          </li>
          
          
        </ul>
      </div>
    </nav>

    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
  export default {
    data () {
      return { }
    },

    methods: {
      logout: function() {
        this.$root.credentials = null;
        this.$router.replace('/');
      }
    }
  }
</script>

<style>
#app {
  font-family: Helvetica, Arial, sans-serif;
  margin-top: 0px;
}
.container {
  width: 100% !important;
}
span.greetings {
  color: white;
  margin: 0 4px;
}
li {
  margin: 0 4px;
}
li.login-commands {
  color: white;
  padding-top: 15px;
  margin-right: 32px;
}
li.login-commands a {
  display: inline;
  padding: 15px 8px;
}
li.user-commands {
  color: white;
  padding-top: 15px;
  margin-right: 32px;
}
li.user-commands span.greetings {
  margin-right: 12px;
}
li.user-commands a {
  display: inline;
  padding: 15px 8px;
}
</style>
