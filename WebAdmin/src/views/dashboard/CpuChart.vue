<script>
import { openapi, methods, routes } from '@/openapi'
import { Line } from 'vue-chartjs'
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities'
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips'
import { random } from '@/shared/utils'

export default {
  extends: Line,
  props: ['height'],
  data: function() {
    return {
      datasets: [],
      datas: {},
      labels: [],
      levels: 10,
      interval: null
    }
  },
  methods: {
    initDatasets () {
      const brandSuccess = getStyle('--success') || '#4dbd74'
      const brandInfo = getStyle('--info') || '#20a8d8'
      const brandDanger = getStyle('--danger') || '#f86c6b'

      openapi(methods.GET, routes.SYSTEMRESOURCES).then(data => {
        Object.keys(data.cpu).forEach(key => {
          this.datas[key] = [];
        });
        this.datasets = Object.keys(data.cpu).map(key => {
          if (key === '_Total') {
            return {
              label: 'Total',
              backgroundColor: hexToRgba(brandSuccess, 10),
              borderColor: brandSuccess,
              pointHoverBackgroundColor: '#fff',
              borderWidth: 3,
              data: this.datas[key]
            }
          } else {
            return {
              label: 'Processor ' + (Number(key) + 1),
              backgroundColor: hexToRgba(brandInfo, 10),
              borderColor: brandInfo,
              pointHoverBackgroundColor: '#fff',
              borderWidth: 2,
              data: this.datas[key]
            }
          }
        });
        for (let i = 0; i < 10; i++) {
          this.labels.push('');
          Object.keys(data.cpu).forEach(key => {
            this.datas[key].push(0);
          });
        }
        this.interval = setInterval(this.watchResources, 1000);
      });
    },
    watchResources() {      
      openapi(methods.GET, routes.SYSTEMRESOURCES).then(data => {
        Object.keys(data.cpu).forEach(key => {
          this.datas[key].shift();
          this.datas[key].push(data.cpu[key]["% Processor Time"]);
        });
        let now = new Date();
        let label = `${now.getHours()}:${now.getMinutes()}:${now.getSeconds()}`;
        this.labels.shift();
        this.labels.push(label)
        this.renderOnce();
      });
    },
    renderOnce() {
      this.renderChart({
        labels: this.labels,
        datasets: this.datasets
      }, {
        animation: false,
        tooltips: {
          enabled: false,
          custom: CustomTooltips,
          intersect: true,
          mode: 'index',
          position: 'nearest',
          callbacks: {
            labelColor: function (tooltipItem, chart) {
              return { backgroundColor: chart.data.datasets[tooltipItem.datasetIndex].borderColor }
            }
          }
        },
        maintainAspectRatio: false,
        scales: {
          xAxes: [{
            gridLines: {
              drawOnChartArea: false,
            }
          }],
          yAxes: [{
            position: 'right',
            ticks: {
              beginAtZero: true,
              maxTicksLimit: 5,
              stepSize: 20,
              max: 100
            },
            gridLines: {
              display: true
            }
          }]
        },
        elements: {
          point: {
            radius: 0,
            hitRadius: 10,
            hoverRadius: 4,
            hoverBorderWidth: 3
          }
        }
      })
    }
  },
  mounted () {
    this.initDatasets();
  },
  beforeDestroy() {
    clearInterval(this.interval);
  }
}
</script>
