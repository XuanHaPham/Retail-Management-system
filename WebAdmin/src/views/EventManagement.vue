<template>
  <div>
    <div v-if="loading">
      <b-progress :value="loading? 50 : 100" :max="100" animated></b-progress>
    </div>
    <b-row v-if="!loading" class="animated fadeIn">
      <b-col :lg="12">
        <b-button class="mb-2 mr-2" @click="refresh">Refresh</b-button>
        <b-button class="mb-2" @click="create" variant="primary">Create Event</b-button>
      </b-col>

      <b-col :lg="current ? 6 : 12">
        <c-table
          :table-data="items"
          :fields="fields"
          hover
          caption="Events"
          :rowClicked="rowClicked"
        ></c-table>
      </b-col>

      <b-col lg="6" v-if="current" class="animated fadeIn">
        <b-card class="card-accent-info" :header="'Detail of `' + current.Title + '`'">
          <b-form>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Title</b-input-group-text>
                </b-input-group-prepend>
                <b-form-input type="text" v-model="current.Title"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <multiselect v-model="selectedTypes" :options="types" :multiple="true" :close-on-select="false" :clear-on-select="false" :preserve-search="true" placeholder="Event types" label="TypeName" track-by="ID" :taggable="true" @input="updateType">
              </multiselect>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Content</b-input-group-text>
                </b-input-group-prepend>
                <b-form-textarea
                  placeholder="Detail content"
                  rows="2"
                  max-rows="2"
                  v-model="current.Content"
                ></b-form-textarea>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Description</b-input-group-text>
                </b-input-group-prepend>
                <b-form-textarea
                  placeholder="Short description"
                  rows="2"
                  max-rows="2"
                  v-model="current.Description"
                ></b-form-textarea>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Location</b-input-group-text>
                </b-input-group-prepend>
                <b-form-input type="text" v-model="current.Location"></b-form-input>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Start Time</b-input-group-text>
                </b-input-group-prepend>
                <flat-pickr class="form-control" :config="config" v-model="current.StartTime"></flat-pickr>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Finish Time</b-input-group-text>
                </b-input-group-prepend>
                <flat-pickr class="form-control" :config="config" v-model="current.FinishTime"></flat-pickr>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend>
                  <b-input-group-text>Time Created</b-input-group-text>
                </b-input-group-prepend>
                <flat-pickr class="form-control" :config="config" v-model="current.TimeCreated"></flat-pickr>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend class="right-margin">
                  <b-input-group-text>Enable</b-input-group-text>
                </b-input-group-prepend>
                <c-switch color="primary" variant="3d" v-model="current.Status"/>
              </b-input-group>
            </b-form-group>
            <b-form-group>
              <b-input-group>
                <b-input-group-prepend class="right-margin">
                  <b-input-group-text>Publish</b-input-group-text>
                </b-input-group-prepend>
                <c-switch color="primary" variant="3d" v-model="current.Publish"/>
              </b-input-group>
            </b-form-group>
            <div
              class="btn-group form-actions animated fadeIn"
              v-if="!detailMessage && current.ID !== ''"
            >
              <b-button @click="update" variant="outline-primary">Update</b-button>
              <b-button @click="remove" type="reset" variant="outline-danger">Remove</b-button>
            </div>
            <div class="form-actions animated fadeIn" v-if="!detailMessage && current.ID === ''">
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
import Multiselect from 'vue-multiselect';
import cTable from "@/views/base/Table.vue";
import { Switch as cSwitch } from "@coreui/vue";
import { openapi, methods, routes } from "@/openapi";
import { flatpickrConfig } from '@/shared/utils'

export default {
  name: "tables",
  components: { Multiselect, cTable, cSwitch },
  data: () => {
    return {
      loading: true,
      items: [],
      types: [],
      selectedTypes: [],
      current: null,
      detailMessage: "",
      fields: [
        { key: "Title", sortable: true },
        { key: "Description" },
        { key: "Location" },
        { key: "TimeCreated", sortable: true },
        { key: "Status", sortable: true },
        { key: "Publish", sortable: true }
      ],
      config: flatpickrConfig
    };
  },
  mounted: function() {
    this.refresh();
    openapi(methods.GET, routes.EVENTTYPES).then(data => this.types = data);
  },
  methods: {
    refresh: function() {
      this.loading = true;
      this.current = null;
      openapi(methods.GET, routes.EVENTS).then(data => {
        this.loading = false;
        this.items = data.filter(row => row.Status == true);
      });
    },
    rowClicked: function(event, i) {
      this.current = event;
      this.selectedTypes = this.current.EventVolunteerTypes.map(v => ({ ID: v.EventTypeID,  TypeName: v.EventType.TypeName }));
    },
    hideSuccess: function() {
      setTimeout(() => {
        this.detailMessage = "";
      }, 1000);
    },
    update: function() {
      this.detailMessage = "Updating...";
      openapi(methods.PUT, routes.EVENTS, this.current).then(data => {
        this.detailMessage = "Updated successfully!";
        this.hideSuccess();
      });
    },
    remove: function() {
      this.detailMessage = "Removing...";
      openapi(methods.DELETE, routes.EVENTS, this.current).then(data => {
        this.detailMessage = "Removed successfully!";
        this.items = this.items.filter(item => item !== this.current);
        this.current = null;
        this.hideSuccess();
      });
    },
    create: function() {
      this.current = {
        ID: "",
        Title: "",
        Description: "",
        Content: "",
        Location: "",
        OrganizationMemberID: "",
        StartTime: new Date(),
        FinishTime: new Date(),
        TimeCreated: new Date(),
        Public: false,
        Status: false
      };
    },
    add: function() {
      this.detailMessage = "Adding...";
      var addObj = this.current;
      addObj.ID = 0;
      openapi(methods.POST, routes.EVENTS, addObj).then(data => {
        this.detailMessage = "Added successfully!";
        this.items.push(data);
        this.current = data;
        this.hideSuccess();
      });
    },
    updateType(value) {
      this.current.EventVolunteerTypes = value.map(v => ({ EventID: this.current.ID, EventTypeID: v.ID }));
    }
  }
};
</script>

<style src="vue-multiselect/dist/vue-multiselect.min.css"></style>