<template>
  <div class="app flex-row align-items-center">
    <div class="container">
      <b-row class="justify-content-center">
        <b-col md="8">
          <b-card-group>
            <b-card no-body class="p-4">
              <b-card-body>
                <b-form>
                  <h1>Login</h1>
                  <p :style="'color: ' + color">{{message}}</p>
                  <b-input-group class="mb-3">
                    <b-input-group-prepend><b-input-group-text><i class="icon-user"></i></b-input-group-text></b-input-group-prepend>
                    <b-form-input type="text" class="form-control" placeholder="Username" autocomplete="username email" v-model="username" @keyup.native.enter="login"/>
                  </b-input-group>
                  <b-input-group class="mb-4">
                    <b-input-group-prepend><b-input-group-text><i class="icon-lock"></i></b-input-group-text></b-input-group-prepend>
                    <b-form-input type="password" class="form-control" placeholder="Password" autocomplete="current-password" v-model="password" @keyup.native.enter="login"/>
                  </b-input-group>
                  <b-row>
                    <b-col cols="6">
                      <b-button variant="primary" class="px-4" @click="login" :disabled="loading">Login</b-button>
                    </b-col>
                    <b-col cols="6" class="text-right">
                      <b-button variant="link" class="px-0">Forgot password?</b-button>
                    </b-col>
                  </b-row>
                </b-form>
              </b-card-body>
            </b-card>
            <b-card no-body class="text-white bg-primary py-5 d-md-down-none" style="width:44%">
              <b-card-body class="text-center">
                <!-- <img id="login-logo" src="img/logo.png" width="100%" /> -->
              </b-card-body>
            </b-card>
          </b-card-group>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
import { base, openapi, methods, routes, setToken, requestToken } from '../../openapi'

export default {
  name: 'Login',
  data: () => ({
    base,
    message: 'Sign In to your account',
    color: 'unset',
    loading: false,
    username: '',
    password: ''
  }),
  methods: {
    login: function() {
      this.loading = true;
      this.message = 'Login in...';
      this.color = 'unset';
      requestToken(this.username, this.password).then(data => {
        this.loading = false;
        if (data.id != null) {
          setToken(data);
          this.message = 'Login successful. Redirecting to dashboard...';
          this.color = 'green';
          this.$router.push('/');
        } else {
          this.message = 'Incorrect username or password';
          this.color = 'red';
        }
      });
    }
  }
}
</script>

<style>
  #login-logo {
    width: 100%;
    filter: contrast(100) grayscale(1) invert(1);
  }
</style>
