<template lang="html" >
  <div class="login row" v-if="!$root.credentials" >
    <div class="col-md-5 col-md-offset-4 text-left" 
    style="border: 1px solid #e0e0e0;background-color:white;height:400px">
      <h2 class="form-title" style="margin-left:150px;margin-top:20px">Login</h2>
     
      <form @submit.prevent="processForm" style="margin-top:40px">
        <div class="label-float">
          <input type="text"  id="username" placeholder=" " v-model="form.email" style="background-color:white">
            <label >Insira seu email</label>
          <span class="error" v-if="error.all">{{error.all}}</span>
        </div>

        <div class="label-float" style="margin-top:40px" >
          <input type="password"  id="password" placeholder=" " v-model="form.senha" style="margin-botton:0px;background-color:white" >
           <label >Insira sua senha</label>
           <span class="error" v-if="error.senha">{{error.senha}}</span>
           <button type="button" id="eye" style="background-color:white;border:none">
               <img v-bind:src="eyeImg"  alt="eye" />
           </button>
        </div>

        <div style="align-items: left;margin-top:40px;margin-left:16px">

            <button type="submit" class="btn btn-primary" 
            style="width:140px;height:40px;font-weight: 700;font-size:16px;  
            display: inline-block;border-radius: 70px;margin-left:0px">Entrar
            </button>

               <div class="link-recuperar-senha">
                  <router-link class="link" :to="{ name: 'forgot-password' }" replace>
                   Recuperar a minha senha
                  </router-link>
              </div>
        </div>
  
        
      </form>

    
      <div style="align-items:left;margin-top:40px;">
      <div class="link-criar-conta">
       <p style="display:inline-block;margin-left:16px">Ainda não tem uma conta? </p>
        <router-link class="link" :to="{ name: 'create-account' }" replace style="display:inline-block;">
          Cadastre-se
        </router-link>
      </div>
      </div>
    </div>
  </div>
</template>

<script>
  import axios from 'axios';
  
  const img = require('../../../assets/eye.png')

  export default {
    data() {
      return {
        form: { email: "", senha: "" },
        error: { },
         eyeImg: img
      }
    },

    methods: {
      processForm: function() {
        axios.post("http://localhost:9090/auth", this.form)
          .then(response => {
            this.$root.credentials = response.data.data;
            this.$router.replace('/');
            this.error = {};
          })
          .catch(error => {
            this.error = error.response.data.errors;
          })
      }
    }
  }

window.onload=function(){


  var pwShown = 0;

document.getElementById("eye").addEventListener("click", function () {
    if (pwShown == 0) {
        pwShown = 1;
        show();
    } else {
        pwShown = 0;
        hide();
    }
}, false);
}
  function show() {
    var p = document.getElementById('password');
    p.setAttribute('type', 'text');
}

function hide() {
    var p = document.getElementById('password');
    p.setAttribute('type', 'password');
}


</script>

<style lang="css" scoped>
div.login {
  margin-top: 32px;
}
div.link-recuperar-senha {

   margin-left:60px;
    display: inline-block;
    margin-right:20px
}
div.link-criar-conta {
  display: inline-block;
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



#pass {
  width: 150px;
  padding-right: 20px;
}

.olho {
  cursor: pointer;
  left: 160px;
  position: absolute;
  width: 20px;
}

</style>