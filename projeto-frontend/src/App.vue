<template>
  <div id="app" style="margin-top:0px">
    <div >
      <nav
        class="navbar navbar-expand-lg navbar-light bg-light navbar-inverse navbar-fixed-top"
      >
        <div class="m-3">
          <router-link class="navbar-brand" :to="{ name: 'home' }"
            >Unirio Compartilha+</router-link
          >

          <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
          >
            <span class="navbar-toggler-icon"></span>
          </button>
        </div>

        <div
          class="collapse navbar-collapse d-flex justify-content-end"
          id="navbarNav "
        >
          <ul v-if="$root.credentials" class="navbar-nav ">
            <li
              class="nav-item d-flex align-items-center"
              v-if="$root.credentials"
            >
              <span class="p-2">Olá {{ $root.credentials.nome }}.</span>
            </li>
            <li class="nav-item active">
              <router-link class="link btn btn-link" :to="{ name: 'home' }"
                >Tela inicial</router-link
              >
            </li>
            <li class="nav-item">
              <router-link
                class="link btn btn-link"
                :to="{ name: 'item-list' }"
                >Meus itens</router-link
              >
            </li>
            <li class=" nav-item">
              <router-link class="link btn btn-link" :to="{ name: 'compartilhamento-list' }"
                >Compartilhamentos</router-link
              >
            </li>
            <li class="nav-item" v-if="$root.credentials">
              <router-link
                class="link btn btn-secondary"
                :to="{ name: 'change-password' }"
                replace
                >Troca senha</router-link
              >
            </li>
            <li class="nav-item" v-if="$root.credentials">
              <a class="link btn btn-danger" @click="logout">Logout</a>
            </li>
          </ul>
          <ul v-else class="navbar-nav ">
            <li class=" nav-item">
              <router-link class="link btn btn-primary" :to="{ name: 'login' }"
                >Entrar</router-link
              >
            </li>
          </ul>
        </div>
      </nav>
    </div>
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },

  methods: {
    logout: function() {
      this.$root.credentials = null;
      this.$router.replace("/");
    },
  },
};
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
  padding-top: 30px;
  color: black;
  margin: 0 4px;
}
li {
  margin: 0 4px;
}
</style>
