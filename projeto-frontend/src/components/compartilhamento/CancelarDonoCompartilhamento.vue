<template lang="html">
  <div class="remove-items-compartilhados row" v-if="this.$root.credentials">
    <div class="col-md-10 col-md-offset-1 text-left">
      <h2 class="form-title">Cancelar compartilhamento</h2>
      <h6 class="form-subtitle">Confirme o cancelamento do compartilhamento.</h6>

      <div class="success" v-if="success">
        O compartilhamento foi cancelado.
      </div>

      <div class="error" v-if="error">
        Ocorreu um erro ao cancelar o compartilhamento.
      </div>

      <div>
        <label class="label">Nome</label>
        <p class="text" >{{compartilhamento.nome}}</p>

        <label class="label">Nome do usuario</label>
        <p class="text" >{{compartilhamento.nome_usuario}}</p>

        <label class="label">Status</label>
        <p class="text" >{{compartilhamento.status}}</p>

        <label class="label">Data Inicio</label>
        <p class="text" >{{compartilhamento.data_inicio}}</p>

        <label class="label">Data Termino</label>
        <p class="text" >{{compartilhamento.data_termino}}</p>

        <button type="submit" class="btn btn-danger" @click="mudarStatus">Cancelar o compartilhamento</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: ['compartilhamento'],

  data() {
    return {
      error: false,
      success: false,

      httpOptions: {
          baseURL: this.$root.config.url,
          headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + this.$root.credentials.token
          }
      },
    }
  },

  methods: {
    mudarStatus: function() {
      axios.get("http://localhost:9090/api/compartilhamento/" + this.compartilhamento.id + "/cancelarDono", this.httpOptions)
        .then(() => {
          this.success = true;
          this.error = false;
          setTimeout(this.goBackToListCompartilhamentos, 3000);
        })
        .catch(() => {
          this.error = true;
          this.success = false;
        });
    },

    goBackToListCompartilhamentos: function() {
      window.history.back();
    }
  }
}
</script>

<style lang="css" scoped>
div.remove-items-compartilhados {
  margin-top: 32px;
}
div.success {
  border: 1px solid green;
  background: lightgreen;
  padding: 8px;
  margin-bottom: 24px;
}
div.error {
  border: 1px solid red;
  background: lightcoral;
  padding: 8px;
  margin-bottom: 24px;
}
p.label {
  color: black;
  font-weight: bold;
  text-align: left;
  display: block;
  font-size: 100%;
  padding: 0px 0px 0px 0px;
  margin-bottom: 4px;
}
p.text {
  margin-bottom: 32px;
}
</style>