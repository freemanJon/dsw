<template lang="html">
  <div v-if="this.$root.credentials">
    <div class="row mt-2">
      <div class="col-12">
        <h2>Detalhes do item</h2>
      </div>
    </div>
    <div class="row mt-3">
      <div class="col-12">
        <div class="card">
          <div class="card-body">
            <h5 class="card-title">{{ item.nome }}</h5>
            <h6 class="card-subtitle mb-2 text-muted">{{ item.tipo }}</h6>
            <p class="card-text">
              {{ item.descricao }}
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="row mt-5">
      <div class="col-9">
        <h4>Historico de compartilhamentos</h4>
      </div>
      <div class="col-3">
        <button class="btn btn-primary" @click="novoCompartilhamento()">
          Novo compartilhamento
        </button>
      </div>
    </div>
    <div class="row mt-4">
      <div class="col-12">
        <table class="table" id="tbCompartilhamentos">
          <thead>
            <tr>
              <th>Data inicio</th>
              <th>Data termino</th>
              <th>Usuario</th>
              <th>Status</th>
              <th>Cancelar</th>
            </tr>
          </thead>

          <tbody>
            <tr
              v-for="compartilhamento in item.compartilhamentos"
              :key="compartilhamento"
            >
              <td>{{ compartilhamento.data_inicio }}</td>
              <td>{{ compartilhamento.data_termino }}</td>
              <td>{{ compartilhamento.nome_usuario }}</td>
              <td>{{ compartilhamento.status }}</td>
              <td>
                <button
                  v-if="
                    compartilhamento.status != 'Cancelado pelo usuario' &&
                      compartilhamento.status != 'Cancelado pelo dono'
                  "
                  class="btn btn-danger"
                  @click="cancelar(compartilhamento)"
                >
                  Cancelar
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      error: {},
      id: this.$route.params.id,
      item: {},
      compartilhamentos: {},
      httpOptions: {
        baseURL: this.$root.config.url,
        headers: {
          Accept: "application/json",
          "Content-Type": "application/json",
          Authorization: "Bearer " + this.$root.credentials.token,
        },
      },
    };
  },

  created: function() {
    this.getItem();
  },

  methods: {
    getItem: function() {
      axios
        .get("http://localhost:9090/api/item/" + this.id, this.httpOptions)
        .then((response) => {
          console.log(response);
          this.item = response.data.data;
          this.compartilhamentos = response.data.data.compartilhamentos;
        })
        .catch((error) => {
          console.log(error);
          this.error = error.response.data.errors;
        });
    },
    novoCompartilhamento: function() {
      this.$router.push({
        name: "compartilhamento-new",
        params: { id: this.id },
      });
    },
    cancelar: function(compartilhamento) {
      this.$router.push({
        name: "compartilhamento-cancelarDono",
        params: { compartilhamento: compartilhamento },
      });
    },
  },
};
</script>
