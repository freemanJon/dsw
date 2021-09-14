<template lang="html">
  <div class="criacao-conta row" v-if="!this.$root.credentials" style="margin-top:0px">
    <div class="col-md-5 col-md-offset-4 text-left" style="border: 1px solid #e0e0e0;background-color:white;height:500px;margin-top:0px">
      <h2 class="form-title" style="margin-left:150px;margin-top:20px">Criar conta</h2>
     
      
      <form @submit.prevent="processForm" >
        <div class="label-float"  >
          <input type="text" id="name" placeholder=" " v-model="form.nome"></input>
          <label >Insira seu nome</label>
          <span class="error" v-if="error.nome">{{error.nome}}</span>
        </div>

        <div class="label-float" style="margin-top:40px" >
          <input type="text" id="email" placeholder=" " v-model="form.email"></input>
          <label >Insira seu e-mail</label>
          <span class="error" v-if="error.email">{{error.email}}</span>
        </div>

        <div class="label-float" style="margin-top:40px" >
          <input type="password"  id="password" placeholder=" " v-model="form.senha"></input>
          <label >Escolha uma senha</label>
          <span class="error" v-if="error.senha">{{error.senha}}</span>
        </div>

        <div class="label-float" style="margin-top:40px" >
          <input type="password"  id="password-repeat" placeholder=" " v-model="form.senhaRepetida"></input>
          <label>Repita a senha</label>
          <span class="error" v-if="error.senhaRepetida">{{error.senhaRepetida}}</span>
        </div>

       <div  style="align-items: left;margin-top:40px;margin-left:16px">
              <button type="submit" class="btn btn-primary" 
                  style="width:140px;height:40px;font-weight: 700;font-size:16px;  
                  display: inline-block;border-radius: 70px;margin-left:0px" >Criar conta
             </button>

             <div class="link-login">
              <router-link class="link" :to="{ name: 'login' }">
                   Já tenho uma conta
                 </router-link>
             </div>
        </div>
       
       </form>

    </div>
  </div>
</template>

<script>
  import axios from 'axios';

  export default {
    data() {
      return {
        form: {
          nome: "",
          email: "",
          senha: "",
          senhaRepetida: "",
        },
        error: { }
      }
    },

    methods: {
      processForm: function() {
        axios.post("http://localhost:9090/api/usuario/novo", this.form)
          .then(response => {
            this.$router.replace('account-created');
            this.error = {};
          })
          .catch(error => {
            this.error = error.response.data.errors;
          });
      }
    }
  }
</script>

<style lang="css" scoped>
div.criacao-conta {
  margin-top: 32px;
}
div.link-login {
 margin-left:110px;
    display: inline-block;
    margin-right:20px
}


.label-float{
  position: relative;
  padding-top: 13px;
  margin-left:16px;
   margin-right:20px
}

.label-float input{
  border: 0;
  border-bottom: 2px solid lightgrey;
  outline: none;
  min-width: 360px;
  font-size: 16px;
  transition: all .3s ease-out;
  -webkit-transition: all .3s ease-out;
  -moz-transition: all .3s ease-out;
  -webkit-appearance:none;
  border-radius:0;
}

.label-float input:focus{
  border-bottom: 2px solid #3951b2;
}

.label-float input::placeholder{
  color:transparent;
}

.label-float label{
  pointer-events: none;
  position: absolute;
  color:#455a64;
  top: 0;
  left: 0;
  margin-top: 13px;
  transition: all .3s ease-out;
  -webkit-transition: all .3s ease-out;
  -moz-transition: all .3s ease-out;
}

.label-float input:required:invalid + label{
  color: red;
}
.label-float input:focus:required:invalid{
  border-bottom: 2px solid red;
}
.label-float input:required:invalid + label:before{
  content: '*';
}
.label-float input:focus + label,
.label-float input:not(:placeholder-shown) + label{
  font-size: 13px;
  margin-top: -6px;
  color: #3951b2;
}


</style>