<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated></b-progress>
    </div>
    <b-row v-if="!loading" class="animated fadeIn">
      <b-col :lg="12">
        <b-button class="mb-2 mr-2" @click="refresh">Refresh</b-button>
        <b-button class="mb-2" @click="create" variant="primary">Add new organization</b-button>
      </b-col>

      <b-col :lg="current ? viewMembers ? 4 : 6 : 12">
        <c-table :table-data="items" :fields="fields" hover caption="Organizations" :rowClicked="rowClicked"></c-table>
      </b-col>

      <b-col :lg="viewMembers ? 4 : 6" v-if="current" class="animated fadeIn">
        <b-card class="card-accent-info" :header="'Detail of `' + current.name + '`'">
          <b-form>
            <b-form-group>
              <b-input-group v-show="current.ID != 0">
                <b-input-group-prepend><b-input-group-text>ID</b-input-group-text></b-input-group-prepend>
                <b-form-input disabled type="text" v-model="current.ID"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group v-for="field in fields" :key="field.key" v-if="['id', 'status', 'creator', 'timeCreate'].indexOf(field.key) === -1">
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>{{field.key}}</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" v-model="current[field.key]"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group v-if="current.Account">
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Creator</b-input-group-text></b-input-group-prepend>
                <b-form-input disabled type="text" v-model="current.Account.UserName"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <c-switch color="primary" variant="3d" v-model="current.status"/>
            </b-form-group>
            <div class="btn-group form-actions animated fadeIn" v-if="!detailMessage && current.ID !== 0">
              <b-button @click="viewMembers = true" type="submit" variant="outline-primary">View Members</b-button>
              <b-button @click="update" type="submit" variant="outline-primary">Update</b-button>
              <b-button @click="remove" type="reset" variant="outline-danger">Remove</b-button>
            </div>
            <div class="form-actions animated fadeIn" v-if="!detailMessage && current.ID === 0">
              <b-button @click="add" type="submit" block variant="outline-primary">Add</b-button>
            </div>
            <div v-if="detailMessage">
              <b-button @click="hideWarning" block variant="outline-warning">{{detailMessage}}</b-button>
            </div>
          </b-form>
        </b-card>
      </b-col>
      <b-col :lg="viewMembers ? 4 : 6" v-if="viewMembers">
        <c-table :table-data="current.OrganizationMembers.map(m => m.Account)" hover :caption="'Members of ' + current.Name"></c-table>        
      </b-col>
    </b-row>

  </div>

</template>

<script>
import cTable from '@/views/base/Table.vue'
import { Switch as cSwitch } from '@coreui/vue'
import { openapi, methods, routes } from '@/openapi'

export default {
  name: 'tables',
  components: {cTable, cSwitch},
  data: () => {
    return {
      loading: true,
      items: [],
      current: null,
      detailMessage: '',
      viewMembers: false,
      fields: [
        {key: 'ID', sortable: true},
        {key: 'Name', sortable: true},
        {key: 'Description'},
        {key: 'TimeCreate', sortable: true},
        {key: 'Creator', formatter: id => `User`},
        {key: 'Status'}
      ],
    }
  },
  mounted: function() {
    this.refresh();
  },
  methods: {
    refresh: function() {
      this.loading = true;
      this.current = null;
      openapi(methods.GET, routes.ORGANIZATIONS).then(data => {
        this.loading = false;
        this.items = data;
      })
    },
    rowClicked: function(organization, i) {
      this.current = organization;
      this.viewMembers = false;
    },
    hideSuccess: function() {
      setTimeout(() => {
        this.detailMessage = ''
      }, 1000);
    },
    hideWarning: function() {
      this.detailMessage = ''
    },
    update: function() {
      this.detailMessage = 'Updating...';
      openapi(methods.PUT, routes.ORGANIZATIONS, this.current).then(data => {
        if (data.Message) {
          this.detailMessage = data.Message;
        } else {
          this.detailMessage = 'Updated successfully!';
          this.hideSuccess();
        }
      });
    },
    remove: function() {
      this.detailMessage = 'Removing...';
      openapi(methods.DELETE, routes.ORGANIZATIONS, this.current).then(data => {
        this.detailMessage = 'Removed successfully!';
        this.items = this.items.filter(item => item !== this.current);
        this.current = null;
        this.hideSuccess();
      });
    },
    create: function() {
      this.current = {
        ID: 0,
        Key: '',
        OrganizationMembers: []
      }
      this.viewMembers = false;
    },
    add: function() {
      this.detailMessage = 'Adding...';
      openapi(methods.POST, routes.ORGANIZATIONS, this.current).then(data => {
        this.detailMessage = 'Added successfully!';
        this.items.push(data);
        this.current = data;
        this.hideSuccess();
      });
    }
  }
}
</script>
