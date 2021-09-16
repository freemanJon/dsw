<template lang="html">
  <div class="container">
    <div v-if="this.$root.credentials">
      <div v-if="countCompartilhamentosNovos != 0" class="row">
        <div class="col-12">
          <div class="card">
            <div class="card-header">
              Notificacoes
            </div>
            <div class="card-body">
              <h5 class="card-title">Compartilhamentos novos</h5>
              <p class="card-text">
                Voce tem {{ countCompartilhamentosNovos }} compartilhados novos.
              </p>
              <router-link
                class="link btn btn-primary"
                :to="{ name: 'home' }"
                replace
                >Ir para Compartilhamentos</router-link
              >
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12 card">
        <div class="card-body">
          <h5 class="card-title">Seja bem-vindo</h5>
          <h6 class="card-subtitle mb-2 text-muted">
            Sistema de compartilhamento Unirio
          </h6>
          <p class="card-text">
            Voce esta no sistema de compartilhamento de itens da Faculdade
            UNIRIO, espero que compartilhe bastante e consiga pegar itens que
            deseja.
          </p>
        </div>
      </div>
    </div>

    <div class="row footer-row">
      <div class="col-md-offset-1 col-md-10">
        <div class="footer">
          <p>
            ©2020 UNIRIO, Universidade Federal do Estado do Rio de Janeiro
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      countCompartilhamentosNovos: 0,
      httpOptions: {
        baseURL: this.$root.config.url,
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization:
            "Bearer " + this.$root.credentials != null
              ? this.$root.credentials.token
              : "",
        },
      },
    };
  },

  created: function() {
    this.contarCompartilhamentosNovos();
  },

  methods: {
    contarCompartilhamentosNovos: function() {
      if (this.$root.credentials != null) {
        console.log(this.httpOptions);
        axios
          .get(
            "http://localhost:9090/api/compartilhamento/abertos",
            this.httpOptions
          )
          .then((response) => {
            this.countCompartilhamentosNovos = parseInt(
              response.data.data.total
            );
            console.log(response);
          })
          .catch((error) => {
            this.error = error.response.data.errors;
          });
      }
    },
  },
};
</script>

<style lang="css" scoped>
div.jumbotron-row {
  margin-top: 32px;
}
.marketing h4 {
  font-size: 20px;
}
.footer {
  margin-top: 64px;
  margin-bottom: 32px;
  padding-top: 8px;
  color: #777;
  border-top: 1px solid #e5e5e5;
}
</style>
