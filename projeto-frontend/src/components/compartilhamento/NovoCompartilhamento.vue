<template lang="html">
  <div class="form-items-compartilhados row" v-if="this.$root.credentials">
    <div class="col-md-10 col-md-offset-1 text-left">
      <h2 class="form-title">Novo compartilhamento</h2>
      <h6 class="form-subtitle">Entre com os dados do novo compartilhamento.</h6>

      <div class="success" v-if="success">
        Os dados do item foram salvos.
      </div>

      <form @submit.prevent="processForm">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="text" class="form-control" id="email" placeholder="Entre com email do destinatario" v-model="compartilhamento.emailDestinatario">
        </div>

        <div class="form-group">
          <label for="dataInicio">Data Inicio</label>
          <input type="date" class="form-control" id="dataInicio" v-model="compartilhamento.dataInicio"/>
        </div>

        <div class="form-group">
          <label for="dataTermino">Data Termino</label>
          <input type="date" class="form-control" id="dataInicio" v-model="compartilhamento.dataTermino"/>
        </div>

        <button type="submit" class="btn btn-primary">Envia</button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      compartilhamento: {emailDestinatario: "", dataInicio: "", dataTermino: "", idItem: this.$route.params.id},
      response:{},
      error: {},
      itens:{},
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
    processForm: function() {
      this.compartilhamento.dataInicio = this.compartilhamento.dataInicio + "T00:00:00"
      this.compartilhamento.dataTermino = this.compartilhamento.dataTermino + "T00:00:00"
      axios.post("http://localhost:9090/api/compartilhamento/novo", this.compartilhamento, this.httpOptions)
        .then(response => {
          this.response = response;
          this.success = true;
          this.error = {};
          setTimeout(this.goBackToList, 3000);
        })
        .catch(error => {
          this.error = error.response.data.errors;
        });
    },

    goBackToList: function() {
      this.$router.push({
        name: "item-getById",
        params: { id: this.$route.params.id}
    })}
  }
}
</script>

<style lang="css" scoped>
div.form-items-compartilhados {
  margin-top: 32px;
}
div.success {
  border: 1px solid green;
  background: lightgreen;
  padding: 8px;
  margin-bottom: 24px;
}
</style>