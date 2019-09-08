<template>
  <AppHeaderDropdown right no-caret>
    <template slot="header">
      <img
        src="img/dropdown.png"
        class="img-avatar"
        alt="admin@bootstrapmaster.com" />
    </template>\
    <template slot="dropdown">
      <b-dropdown-header tag="div" class="text-center"><strong>Settings</strong></b-dropdown-header>
      <b-dropdown-item to="/profile?0"><i class="fa fa-user" /> View Profile</b-dropdown-item>
      <b-dropdown-item to="/profile?1"><i class="fa fa-wrench" /> Update Profile</b-dropdown-item>
      <b-dropdown-item to="/profile?2"><i class="fa fa-shield" /> Change password</b-dropdown-item>
      <b-dropdown-item @click="logout"><i class="fa fa-lock" /> Logout</b-dropdown-item>
    </template>
  </AppHeaderDropdown>
</template>

<script>
import { HeaderDropdown as AppHeaderDropdown } from '@coreui/vue'
import { clearToken, getAvatar } from '../openapi';
export default {
  name: 'DefaultHeaderDropdownAccnt',
  components: {
    AppHeaderDropdown
  },
  data: () => {
    return { itemsCount: 42 }
  },
  computed: {
    avatar: () => getAvatar()
  },
  methods: {
    logout: function() {
      clearToken();
      this.$router.push('/pages/login');
    }
  }
}
</script>
