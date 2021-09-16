<template lang="html">
  <div class="remove-items-compartilhados row" v-if="this.$root.credentials">
    <div class="col-md-10 col-md-offset-1 text-left">
      <h2 class="form-title">Remoção de item compartilhado</h2>
      <h6 class="form-subtitle">Confirme a remoção do item compartilhado.</h6>

      <div class="success" v-if="success">
        O item foi removido do sistema.
      </div>

      <div class="error" v-if="error">
        Ocorreu um erro ao remover o item do sistema.
      </div>

      <div>
        <label class="label">Nome</label>
        <p class="text mt-3" >{{item.nome}}</p>

        <label class="label">Descrição</label>
        <p class="text mt-3" >{{item.descricao}}</p>

        <label class="label">Tipo</label>
        <p class="text mt-3" >{{item.tipo}}</p>

        <button type="submit" class="btn btn-danger mt-3" @click="remove">Remover o item</button>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: ['item'],

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
    remove: function() {
      axios.delete("http://localhost:9090/api/item/" + this.item.id, this.httpOptions)
        .then(() => {
          this.success = true;
          this.error = false;
          setTimeout(this.goBackToList, 3000);
        })
        .catch(() => {
          this.error = true;
          this.success = false;
        });
    },

    goBackToList: function() {
      this.$router.replace('/item/list');
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