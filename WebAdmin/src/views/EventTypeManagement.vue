<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated></b-progress>
    </div>
    <b-row v-if="!loading" class="animated fadeIn">
      <b-col :lg="12">
        <b-button class="mb-2 mr-2" @click="refresh">Refresh</b-button>
        <b-button class="mb-2" @click="create" variant="primary">New Event Type</b-button>
      </b-col>

      <b-col :lg="current ? 6 : 12">
        <c-table
          :table-data="items"
          :fields="fields"
          hover
          caption="Event Types"
          :rowClicked="rowClicked"
        ></c-table>
      </b-col>

      <b-col lg="6" v-if="current" class="animated fadeIn">
        <b-card class="card-accent-info" :header="'Detail of `' + current.TypeName + '`'">
          <b-form>
            <b-input-group v-show="current.ID != 0">
              <b-input-group-prepend><b-input-group-text>ID</b-input-group-text></b-input-group-prepend>
              <b-form-input disabled type="text" v-model="current.ID"></b-form-input>
            </b-input-group>
            <b-form-group v-for="field in fields" :key="field.key">
              <b-input-group v-if="['ID', 'Status'].indexOf(field.key) === -1">
                <b-input-group-prepend><b-input-group-text>{{field.key}}</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" v-model="current[field.key]"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <c-switch color="primary" variant="3d" v-model="current.status"/>
            </b-form-group>
            <div
              class="btn-group form-actions animated fadeIn"
              v-if="!detailMessage && current.ID !== 0"
            >
              <b-button @click="update" variant="outline-primary">Update</b-button>
              <b-button @click="remove" type="reset" variant="outline-danger">Remove</b-button>
            </div>
            <div class="form-actions animated fadeIn" v-if="!detailMessage && current.ID === 0">
              <b-button @click="add" block variant="outline-primary">Add</b-button>
            </div>
            <div v-if="detailMessage">
              <b-button disabled block variant="outline-warning">{{detailMessage}}</b-button>
            </div>
          </b-form>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>
<style>
.right-margin {
  margin-right: 20px;
}
</style>
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
      detailMessage: "",
      fields: [
        { key: "ID", sortable: true },
        { key: "TypeName", sortable: true },
        { key: "Description" },
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
      openapi(methods.GET, routes.EVENTTYPES).then(data => {
        this.loading = false;
        this.items = data;
      });
      openapi(methods.GET, routes.EVENTTYPES).then(data => {
        this.loading = false;
        this.items.OrganizationMemberID = data.OrganizationMembers[0].ID;
      });
    },
    rowClicked: function(event, i) {
      this.current = event;
    },
    hideSuccess: function() {
      setTimeout(() => {
        this.detailMessage = "";
      }, 1000);
    },
    update: function() {
      this.detailMessage = "Updating...";
      openapi(methods.PUT, routes.EVENTTYPES, this.current).then(data => {
        this.detailMessage = "Updated successfully!";
        this.hideSuccess();
      });
    },
    remove: function() {
      this.detailMessage = "Removing...";
      openapi(methods.DELETE, routes.EVENTTYPES, this.current).then(data => {
        this.detailMessage = "Removed successfully!";
        this.items = this.items.filter(item => item !== this.current);
        this.current = null;
        this.hideSuccess();
      });
    },
    create: function() {
      this.current = {
          ID: 0
      }
    },
    add: function() {
      this.detailMessage = "Adding...";
      var addObj = this.current;
      addObj.ID = 0;
      openapi(methods.POST, routes.EVENTTYPES, addObj).then(data => {
        this.detailMessage = "Added successfully!";
        this.items.push(data);
        this.current = data;
        this.hideSuccess();
      });
    }
  }
};
</script>