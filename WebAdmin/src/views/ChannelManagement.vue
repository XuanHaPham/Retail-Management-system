<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated></b-progress>
    </div>
    <b-row v-if="!loading" class="animated fadeIn">
      <b-col :lg="12">
        <b-button class="mb-2 mr-2" @click="refresh">Refresh</b-button>
        <b-button class="mb-2" @click="create" variant="primary">Add new Channel</b-button>
      </b-col>

      <b-col :lg="current ? 6 : 12">
        <c-table :table-data="items" :fields="fields" hover caption="Channel" :rowClicked="rowClicked"></c-table>
      </b-col>

      <b-col lg="6" v-if="current" class="animated fadeIn">
        <b-card class="card-accent-info" :header="'Detail of `' + current.Title + '`'">
          <b-form>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>ID</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" disabled v-model="current.ID"></b-form-input>
              </b-input-group>
            </b-form-group>

            <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Overview</b-input-group-text></b-input-group-prepend>
                <b-form-textarea placeholder="Enter content here" rows="2" max-rows="2" v-model="current.Overview"></b-form-textarea>
              </b-input-group>
            </b-form-group>

            <b-form-group v-for="field in fields" :key="field.key">
              <b-input-group
                v-if="['ID','Status','Overview'].indexOf(field.key) === -1"
              >
                <b-input-group-prepend>
                  <b-input-group-text>{{field.key}}</b-input-group-text>
                </b-input-group-prepend>
                <b-form-input type="text" v-model="current[field.key]"></b-form-input>
              </b-input-group>
            </b-form-group>


            <!-- <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Title</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" v-model="current.Title"></b-form-input>
              </b-input-group>
            </b-form-group> -->

            <!-- <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Content</b-input-group-text></b-input-group-prepend>
                <b-form-textarea placeholder="Enter content here" rows="2" max-rows="2" v-model="current.Content"></b-form-textarea>
              </b-input-group>
            </b-form-group> -->

            <!-- <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Image URL</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" v-model="current.ImageURL"></b-form-input>
              </b-input-group>
            </b-form-group> -->

            <!-- <b-form-group>
                <b-input-group>
                    <b-input-group-prepend class="right-margin"><b-input-group-text>Public</b-input-group-text></b-input-group-prepend>
                    <c-switch disabled color="primary" variant="3d" v-model="current.Public"/>
                </b-input-group>
            </b-form-group> -->

            <b-form-group>
                <b-input-group>
                    <b-input-group-prepend class="right-margin"><b-input-group-text>Status</b-input-group-text></b-input-group-prepend>
                    <c-switch color="primary" variant="3d" v-model="current.Status"/>
                </b-input-group>
            </b-form-group>
            <div class="btn-group form-actions animated fadeIn" v-if="!detailMessage && current.ID !== ''">
              <b-button @click="update" type="submit" variant="outline-primary">Update</b-button>
              <b-button @click="remove" type="reset" variant="outline-danger">Remove</b-button>
            </div>
            <div class="form-actions animated fadeIn" v-if="!detailMessage && current.ID === ''">
              <b-button @click="add" type="submit" block variant="outline-primary">Add</b-button>
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
import cTable from '@/views/base/Table.vue'
import { Switch as cSwitch } from '@coreui/vue'
import { openapi, getUsername, methods, routes } from '@/openapi'

export default {
  name: 'tables',
  components: {cTable, cSwitch},
  data: () => {
    return {
      loading: true,
      items: [],
      current: null,
      detailMessage: '',
      fields: [
        {key: 'ID', sortable: true},
        {key: 'CompanyName', sortable: true},
        {key: 'Overview'},
        {key: 'ShippingFee', sortable: true},
        {key: 'Status'}
      ]
    }
  },
  mounted: function() {
    this.refresh();
  },
  methods: {
    refresh: function() {
      this.loading = true;
      this.current = null;
      openapi(methods.GET, routes.NEWS).then(data => {
        this.loading = false;
        this.items = data;
      });
    },
    rowClicked: function(channel, i) {
      this.current = channel;
    },
    hideSuccess: function() {
      setTimeout(() => {
        this.detailMessage = ''
      }, 1000);
    },
    update: function() {
      this.detailMessage = 'Updating...';
      openapi(methods.PUT, routes.NEWS, this.current).then(data => {
        this.detailMessage = 'Updated successfully!';
        this.hideSuccess();
      });
    },
    remove: function() {
      this.detailMessage = 'Removing...';
      openapi(methods.DELETE, routes.NEWS, this.current).then(data => {
        this.detailMessage = 'Removed successfully!';
        this.items = this.items.filter(item => item !== this.current);
        this.current = null;
        this.hideSuccess();
      });
    },
    create: function() {
      this.current = {
        ID: '',
        Title: '',
        Content: '',
        ImageURL: '',
        OrganizationMemberID: '',
        Public: true,
        TimeCreated: null,
        Status: true
      }
    },
    add: async function() {
      this.detailMessage = 'Adding...';
      var addObj = {
        ID: 0,
        Title: this.current.Title,
        Content: this.current.Content,
        ImageURL: this.current.ImageURL,
        OrganizationMemberID: null,
        Public: this.current.Public,
        TimeCreated: new Date(),
        Status: this.current.Status
      };
      await openapi(methods.POST, 
        routes.ORGANIZATIONMEMBER, 
        {"UserName": getUsername(), "OrgName": "Default"}).then(data => {
          addObj.OrganizationMemberID = data.ID;
      });
      openapi(methods.POST, routes.NEWS, addObj).then(data => {
        this.detailMessage = 'Added successfully!';
        this.items.push(data);
        this.current = data;
        this.hideSuccess();
      });
    }
  }
}
</script>