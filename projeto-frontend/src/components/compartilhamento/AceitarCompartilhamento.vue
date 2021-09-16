<template lang="html">
  <div class="remove-items-compartilhados row" v-if="this.$root.credentials">
    <div class="col-md-10 col-md-offset-1 text-left">
      <h2 class="form-title">Aceitar compartilhamento</h2>
      <h6 class="form-subtitle">Confirme a aceitacao do compartilhamento.</h6>

      <div class="success" v-if="success">
        O compartilhamento foi aceito.
      </div>

      <div class="error" v-if="error">
        Ocorreu um erro ao aceitar o compartilhamento.
      </div>

      <div>
        <label class="label">Nome</label>
        <p class="text mt-3" >{{compartilhamento.nome}}</p>

        <label class="label">Nome do Dono</label>
        <p class="text mt-3" >{{compartilhamento.nome_usuario}}</p>

        <label class="label">Status</label>
        <p class="text mt-3" >{{compartilhamento.status}}</p>

        <label class="label">Data Inicio</label>
        <p class="text mt-3" >{{compartilhamento.data_inicio}}</p>

        <label class="label">Data Termino</label>
        <p class="text mt-3" >{{compartilhamento.data_termino}}</p>

        <button type="submit" class="btn btn-primary mt-3" @click="mudarStatus()">Aceitar o compartilhamento</button>
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
      axios.get("http://localhost:9090/api/compartilhamento/" + this.compartilhamento.id + "/aceitar", this.httpOptions)
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
      this.$router.replace('/compartilhamento/list');
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