<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated variant="danger"></b-progress>
    </div>
    <div v-if="!loading" class="animated fadeIn">
      <b-tabs v-model="tabIndex">
        <b-tab>
            <template slot="title">
            <i class="icon-calculator"></i> {{tabs[0]}}
            </template>
            <!-- <img id="avatar" src="/img/logo.png"/> -->
            <div class="text-center">
              <h1 class="display-4">{{infos.Account.Email}}</h1>
              <div v-if="infos.Account.Id"><b>Id: </b><span>{{infos.Account.Id}}</span></div>
              <div v-if="infos.Account.UserName"><b>UserName: </b><span>{{infos.Account.UserName}}</span></div>
              <div v-if="infos.Account.Fullname"><b>Fullname: </b><span>{{infos.Account.Fullname}}</span></div>
              <div v-if="infos.Account.Point"><b>Point: </b><span>{{infos.Account.Point}}</span></div>
              <div v-if="infos.Account.Address"><b>Address: </b><span>{{infos.Account.Address}}</span></div>
              <div v-if="infos.Account.Status"><b>Status: </b><span>{{infos.Account.Status}}</span></div>
              <div v-if="infos.Account.EmailConfirmed"><b>EmailConfirmed: </b><span>{{infos.Account.EmailConfirmed}}</span></div>
              <div v-if="infos.Account.PhoneNumber"><b>PhoneNumber: </b><span>{{infos.Account.PhoneNumber}}</span></div>
              <div v-if="infos.Account.TwoFactorEnabled"><b>TwoFactorEnabled: </b><span>{{infos.Account.TwoFactorEnabled}}</span></div>
              <div v-if="infos.Account.LockoutEndDateUtc"><b>LockoutEndDateUtc: </b><span>{{infos.Account.LockoutEndDateUtc}}</span></div>
              <div v-if="infos.Account.LockoutEnabled"><b>LockoutEnabled: </b><span>{{infos.Account.LockoutEnabled}}</span></div>
              <div v-if="infos.Account.AccessFailedCount"><b>AccessFailedCount: </b><span>{{infos.Account.AccessFailedCount}}</span></div>
              <div><b-button variant="primary" @click="navigate(1)">Edit</b-button></div>
            </div>
        </b-tab>
        <b-tab>
            <template slot="title">
            <i class="fa fa-wrench"></i> {{tabs[1]}}
            </template>
            <b-card :header="infos.Account.Email">
              <b-form>
                <b-form-group
                  label="ID"
                  :label-cols="3"
                  :horizontal="true">
                  <b-form-input type="text" v-model="infos.Account.Id" disabled></b-form-input>
                </b-form-group>
                <b-form-group v-for="field in profileFields" :key="field"
                  :label="field"
                  :label-cols="3"
                  :horizontal="true">
                  <b-form-input type="text" v-model="infos.Account[field]"></b-form-input>
                </b-form-group>
                <b-form-group>
                  <b-button v-if="profileUpdater.status === 'ready'" block variant="outline-info" @click="updateProfile">{{profileUpdater.message}}</b-button>
                  <b-button v-if="profileUpdater.status === 'updating'" block disabled variant="outline-info">Updating...</b-button>
                  <b-button v-if="profileUpdater.status === 'success'" block variant="success">Profile updated successfully!</b-button>
                  <b-button v-if="profileUpdater.status === 'error'" block variant="danger" @click="profileUpdater.status = 'ready'">{{profileUpdater.message}}</b-button>
                </b-form-group>
              </b-form>
            </b-card>
        </b-tab>
        <b-tab >
            <template slot="title">
            <i class="fa fa-shield"></i> {{tabs[2]}}
            </template>

            <b-card class="card-accent-warning" :header="infos.Account.Email">
              <b-form>
                <b-form-group
                  label="Old password"
                  :label-cols="3"
                  :horizontal="true">
                  <b-form-input type="password" v-model="passwords.OldPassword"></b-form-input>
                </b-form-group>
                <b-form-group
                  description="Password must be >= 8 characters, must have lowercase, uppercase and digits."
                  label="New password"
                  :label-cols="3"
                  :horizontal="true">
                  <b-form-input type="password" v-model="passwords.NewPassword"></b-form-input>
                </b-form-group>
                <b-form-group
                  description="Password must be >= 8 characters, must have lowercase, uppercase and digits."
                  label="New password"
                  :label-cols="3"
                  :horizontal="true">
                  <b-form-input type="password" v-model="passwords.ConfirmPassword"></b-form-input>
                </b-form-group>
                <b-form-group>
                  <b-button v-if="passwords.status === 'ready'" block variant="outline-warning" @click="changePassword">{{passwords.message}}</b-button>
                  <b-button v-if="passwords.status === 'changing'" block disabled variant="outline-warning" @click="changePassword">Changing...</b-button>
                  <b-button v-if="passwords.status === 'changed'" block disabled variant="success" @click="changePassword">Password changed successfully!</b-button>
                  <b-button v-if="passwords.status === 'error'" block variant="danger" @click="changePassword">{{passwords.message}}</b-button>
                </b-form-group>
              </b-form>
            </b-card>
        </b-tab>
      </b-tabs>
    </div>
  </div>
</template>

<script>
import { openapi, methods, routes, getAvatar } from '../openapi';

export default {
  name: 'tabs',
  data () {
    return {
      tabIndex: 0,
      tabs: [
        'Overview',
        'Update',
        'Change Password'
      ],
      loading: true,
      infos: null,
      profileFields: [
        'UserName',
        'Fullname',
        'Address',
        'Email',
        'PhoneNumber'
      ],
      profileUpdater: {
        status: 'ready',
        message: 'Update Profile'
      },
      passwords: {
        OldPassword: '',
        NewPassword: '',
        ConfirmPassword: '',
        status: 'ready',
        message: 'Change Password'
      }
    }
  },
  computed: {
    avatar: () => getAvatar()
  },
  mounted() {
    this.urlNavigate();
    openapi(methods.GET, routes.USERINFO).then(data => {
      this.loading = false;
      this.infos = data;
    });
  },
  watch: {
    '$route.query': function() {
      this.urlNavigate();
    }
  },
  methods: {
    navigate(index) {
      this.tabIndex = index
    },
    urlNavigate() {
      let href = window.location.href;
      if (href[href.length - 2] === '?') {
        this.tabIndex = Number(href[href.length - 1]);
      }
    },
    updateProfile() {
      this.profileUpdater.status = 'updating';
      openapi(methods.PUT, routes.UPDATEPROFILE, this.infos.Account).then(data => {
        console.log(data);
        if (data.Message) {
          this.profileUpdater.status = 'error';
          this.profileUpdater.message = data.Message + ' Try again';
        } else {
          this.profileUpdater.status = 'success';
          setTimeout(() => {
            this.profileUpdater.status = 'ready';
          }, 1000);
        }
      })
    },
    changePassword() {
      this.passwords.status = 'changing';
      openapi(methods.POST, routes.CHANGEPASSWORD, this.passwords).then(data => {
        if (data.ModelState) {
          this.passwords.status = 'error';
          this.passwords.message = data.ModelState[Object.keys(data.ModelState)[0]][0] + ' Try again';
        } else {
          this.passwords.status = 'changed';
        }
      })
    }
  }
}
</script>

<style scoped>
  #avatar {
    border-radius: 100%;
    width: 200px;
    margin: 20px auto;
    display: block;
  }
</style>
