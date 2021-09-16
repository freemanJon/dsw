<template lang="html">
  <div v-if="this.$root.credentials">
    <div class="lista-items-compartilhados row">
      <div class="col-md-10 col-md-offset-1 text-left">
        <div>
          <div class="header">
            <h2 class="form-title">Compartilhamentos</h2>
          </div>
          <div class="clear"></div>
        </div>
      </div>
    </div>
    <div class="row mt-5">
      <div class="col-12">
        <table class="table" id="tbItens">
          <thead>
            <tr>
              <th>Item</th>
              <th>Dono</th>
              <th>Status</th>
              <th>Data Inicio</th>
              <th>Data Termino</th>
              <th>Aceitar</th>
              <th>Recusar</th>
              <th>Cancelar</th>
            </tr>
          </thead>

          <tbody>
            <tr v-for="compartilhamento in compartilhamentos" :key="compartilhamento">
              <td>{{ compartilhamento.nome }}</td>
              <td>{{ compartilhamento.nome_usuario }}</td>
              <td>{{ compartilhamento.status }}</td>
              <td>{{ compartilhamento.data_inicio }}</td>
              <td>{{ compartilhamento.data_termino }}</td>
              <td>
                <button v-if="compartilhamento.status == 'Aberto'" class="btn btn-primary" @click="aceita(compartilhamento)">
                  Aceitar
                </button>
              </td>
              <td>
                <button v-if="compartilhamento.status == 'Aberto'" class="btn btn-danger" @click="recusa(compartilhamento)">
                  Recusar
                </button>
              </td>
              <td>
                <button v-if="compartilhamento.status != 'Aberto' && compartilhamento.status != 'Cancelado pelo usuario' && compartilhamento.status != 'Cancelado pelo dono'" class="btn btn-danger" @click="cancelaUsuario(compartilhamento)">
                  Cancelar
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="row">
      <div class="col-12">
        <div>
          <div
            class="page-item first"
            :class="{ disable: this.page == 1 }"
            @click="moveTo(page - 1)"
          >
            &lt;&lt;
          </div>
          <div class="page-item" v-show="page > 3" @click="moveTo(page - 3)">
            {{ page - 3 }}
          </div>
          <div class="page-item" v-show="page > 2" @click="moveTo(page - 2)">
            {{ page - 2 }}
          </div>
          <div class="page-item" v-show="page > 1" @click="moveTo(page - 1)">
            {{ page - 1 }}
          </div>
          <div class="page-item current disable">{{ page }}</div>
          <div
            class="page-item"
            v-show="totalPages > page"
            @click="moveTo(page + 1)"
          >
            {{ page + 1 }}
          </div>
          <div
            class="page-item"
            v-show="totalPages > page + 1"
            @click="moveTo(page + 2)"
          >
            {{ page + 2 }}
          </div>
          <div
            class="page-item"
            v-show="totalPages > page + 2"
            @click="moveTo(page + 3)"
          >
            {{ page + 3 }}
          </div>
          <div
            class="page-item last"
            :class="{ disable: this.page == this.totalPages }"
            @click="moveTo(page + 1)"
          >
            &gt;&gt;
          </div>
          <div class="clear"></div>
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
      page: 1,
      totalPages: 1,
      compartilhamentos: [],

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
    this.processForm();
  },

  methods: {
    processForm: function() {
      axios
        .get(
          "http://localhost:9090/api/compartilhamento/lista?sort=&per_page=10&page=" +
            this.page,
          this.httpOptions
        )
        .then((response) => {
          this.compartilhamentos = response.data.data.data;
          this.page = response.data.data.current_page;
          this.totalPages = response.data.data.last_page;
          this.error = {};
        })
        .catch((error) => {
          this.error = error.response.data.errors;
        });
    },

    moveTo: function(page) {
      if (page < 1) page = 1;

      if (page > this.totalPages) page = this.totalPages;

      this.page = page;
      this.processForm();
    },

    aceita: function(compartilhamento) {
      this.$router.push({
        name: "compartilhamento-aceitar",
        params: { compartilhamento: compartilhamento },
      });
    },

    recusa: function(compartilhamento) {
      this.$router.push({
        name: "compartilhamento-rejeitar",
        params: { compartilhamento: compartilhamento },
      });
    },

    cancelaUsuario: function(compartilhamento) {
      this.$router.push({
        name: "compartilhamento-cancelarUsuario",
        params: { compartilhamento: compartilhamento },
      });
    }
  }
};
</script>

<style lang="css" scoped>
div.lista-items-compartilhados {
  margin-top: 32px;
}
th.commands {
  width: 48px;
}
div.page-item {
  color: #2973b7;
  text-decoration: none;
  cursor: pointer;
  padding: 10px;
  border: 1px solid lightgray;
  margin: 0px -1px 0px 0px;
  float: left;
}
div.page-item.first {
  border-top-left-radius: 8px;
  border-bottom-left-radius: 8px;
}
div.page-item.last {
  border-top-right-radius: 8px;
  border-bottom-right-radius: 8px;
}
div.page-item.disable {
  color: gray;
  cursor: auto;
}
div.page-item.current {
  background-color: lightgray;
}
div.clear {
  clear: both;
}
div.header {
  float: left;
}
div.new-button {
  float: right;
  text-align: right;
}
</style>
