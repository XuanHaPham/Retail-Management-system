<template>
    <div>
        <b-progress v-if="loading" :value="loading? 50 : 100" :max="100" animated></b-progress>
        <b-tabs v-if="!loading" class="animated fadeIn mb-4">
            <b-tab v-for="info in infos" :key="info.title" :title="info.title" class="animated fadeIn">
                <div v-html="info.content"></div>
            </b-tab>
        </b-tabs>
    </div>
</template>

<script>
import { base } from '../openapi';

export default {
    data() {
        return {
            loading: true,
            infos: []
        }
    },
    mounted() {
        fetch(base).then(res => res.text()).then(html => {
            let div = document.createElement('div');
            div.innerHTML = html;
            this.infos = Array.from(div.querySelectorAll('table.server-info')).map(e => ({
                title: e.querySelector('h2').innerText,
                content: e.outerHTML
            }));
            this.loading = false;
        })
    }
}
</script>

<style>
    table{width:100%;max-width:100%;margin-bottom:1rem;background-color:transparent}table td,table th{padding:.75rem;vertical-align:top;border-top:1px solid #dee2e6}table thead th{vertical-align:bottom;border-bottom:2px solid #dee2e6}table tbody+tbody{border-top:2px solid #dee2e6}table table{background-color:#fff}
    caption {
        caption-side: top;
    }
</style>
