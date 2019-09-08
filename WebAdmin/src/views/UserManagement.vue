<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated></b-progress>
    </div>
    <b-row v-if="!loading" class="animated fadeIn">
      <b-col :lg="12">
        <b-button class="mb-2 mr-2" @click="refresh">Refresh</b-button>
        <b-button class="mb-2" @click="create" variant="primary">Add new user</b-button>
      </b-col>

      <b-col :lg="current ? 6 : 12">
        <c-table
          :table-data="items"
          :fields="fields"
          hover
          caption="Users"
          :rowClicked="rowClicked"
        ></c-table>
      </b-col>

      <b-col lg="6" v-if="current" class="animated fadeIn">
        <b-card class="card-accent-info" :header="'Detail of `' + current.username + '`'">
          <b-form>
            <b-form-group v-for="field in fields" :key="field.key">
              <b-input-group v-if="['Status'].indexOf(field.key) === -1">
                <b-input-group-prepend>
                  <b-input-group-text>{{field.key}}</b-input-group-text>
                </b-input-group-prepend>
                <b-form-input type="text" v-model="current[field.key]"></b-form-input>
              </b-input-group>
            </b-form-group>
            
            <b-form-group>
                <b-input-group>
                    <b-input-group-prepend class="right-margin"><b-input-group-text>Status</b-input-group-text></b-input-group-prepend>
                    <c-switch color="primary" variant="3d" v-model="current.Status"/>
                </b-input-group>
            </b-form-group>

            <!-- <b-form-group>
              <b-input-group-prepend>
                <b-input-group-text>Status</b-input-group-text>
                </b-input-group-prepend>
              <c-switch color="primary" variant="3d" v-model="current.Status"/>
            </b-form-group> -->

           <div class="btn-group form-actions animated fadeIn" v-if="!detailMessage && current.id !== 0">
              <!-- <b-button @click="viewMembers = true" type="submit" variant="outline-primary">View Members</b-button> -->
              <b-button @click="update" type="submit" variant="outline-primary">Update</b-button>
              <b-button @click="remove" type="reset" variant="outline-danger">Remove</b-button>
            </div>
            <div class="form-actions animated fadeIn" v-if="!detailMessage && current.id === 0">
              <b-button @click="add" type="submit" block variant="outline-primary">Add</b-button>
            </div>
            <div v-if="detailMessage">
              <b-button @click="hideWarning" block variant="outline-warning">{{detailMessage}}</b-button>
            </div>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import cTable from "@/views/base/Table.vue";
import { Switch as cSwitch } from "@coreui/vue";
import { openapi, methods, routes } from "@/openapi";

export default {
  name: "tables",
  components: { cTable, cSwitch },
  data: () => {
    return {
      loading: true,
      items: [],
      current: null,
      detailMessage: '',
      fields: [
        { key: "id", sortable: true },
        { key: "username", sortable: true },
        { key: "fullName", sortable: true },
        { key: "email", sortable: true },
        { key: "phoneNumber" },
        { key: "address" },
        { key: "dob", sortable: true },
        { key: "shippingAddress", sortable: true },
        { key: "Status" },
      ]
    };
  },
  mounted: function() {
    this.refresh();
  },
  methods: {
    refresh: function() {
      this.loading = true;
      this.current = null;
      openapi(methods.GET, routes.USERS).then(data => {
        this.loading = false;
        this.items = data;
      });
    },
    rowClicked: function(user, i) {
      this.current = user;
    },
    hideSuccess: function() {
      setTimeout(() => {
        this.detailMessage = "";
      }, 1000);
    },
    update: function() {
      this.detailMessage = 'Updating...';
      openapi(methods.DELETE, routes.USERS, this.current).then(data => {
        if (data.Message) {
          this.detailMessage = data.Message;
        } else {
          this.detailMessage = 'Updated successfully!';
          this.hideSuccess();
        }
      });
    },
    hideWarning: function() {
      this.detailMessage = "";
    },
    create: function() {
      this.current = {
        id: 0,
        username: '',
        status: true
      }
      this.viewMembers = false;
    },
    add: function() {
      this.detailMessage = 'Adding...';
      openapi(methods.POST, routes.USERS, this.current).then(data => {
        this.detailMessage = 'Added successfully!';
        // this.items.push(data);
        this.current = data;
        this.hideSuccess();
      });}
  }
};
</script>
