<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated></b-progress>
    </div>
    <b-row v-if="!loading" class="animated fadeIn">
      <b-col :lg="12">
        <b-button class="mb-2 mr-2" @click="refresh">Refresh</b-button>
        <b-button class="mb-2" @click="create" variant="primary">Add new Role</b-button>
      </b-col>

      <b-col :lg="current ? 6 : 12">
        <c-table :table-data="items" :fields="fields" hover caption="Role" :rowClicked="rowClicked"></c-table>
      </b-col>

      <b-col lg="6" v-if="current" class="animated fadeIn">
        <b-card class="card-accent-info" :header="'Detail of `' + current.name + '`'">
          <b-form>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Id</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" disabled v-model="current.id"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend><b-input-group-text>Name</b-input-group-text></b-input-group-prepend>
                <b-form-input type="text" v-model="current.name"></b-form-input>
              </b-input-group>
            </b-form-group>
            <!-- <div class="btn-group form-actions animated fadeIn" v-if="!detailMessage && current.id !== ''">
              <b-button @click="update" type="submit" variant="outline-primary">Update</b-button>
              <b-button @click="remove" type="reset" variant="outline-danger">Remove</b-button> 
            </div> -->
            <div class="form-actions animated fadeIn" v-if="!detailMessage && current.id === ''">
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
      fields: [
        {key: 'id', sortable: true},
        {key: 'name', sortable: true}
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
      openapi(methods.GET, routes.ROLES).then(data => {
        this.loading = false;
        this.items = data;
      })
    },
    rowClicked: function(role, i) {
      this.current = role;
    },
    hideSuccess: function() {
      setTimeout(() => {
        this.detailMessage = ''
      }, 1000);
    },
    update: function() {
      this.detailMessage = 'Updating...';
      openapi(methods.POST, routes.ROLES, {}, this.current).then(data => {
        this.detailMessage = 'Updated successfully!';
        this.hideSuccess();
      });
    },
    remove: function() {
      this.detailMessage = 'Removing...';
      openapi(methods.DELETE, routes.ROLES, this.current).then(data => {
        this.detailMessage = 'Removed successfully!';
        this.items = this.items.filter(item => item !== this.current);
        this.current = null;
        this.hideSuccess();
      });
    },
    create: function() {
      this.current = {
        id: '',
        name: ''
      }
    },
    add: function() {
      this.detailMessage = 'Adding...';
      var addObj = {id: 0, name: this.current.name};
      openapi(methods.POST, routes.ROLES, addObj).then(data => {
        this.detailMessage = 'Added successfully!';
        this.items.push(data);
        this.current = data;
        this.hideSuccess();
      });
    }
  }
}
</script>