<script>
import { Doughnut } from 'vue-chartjs'

export default {
  extends: Doughnut,
  props: ['free', 'total'],
  mounted () {
    this.renderChart({
      labels: ['Used (GB)', 'Available (GB)'],
      datasets: [
        {
          backgroundColor: [
            '#E46651',
            '#41B883',
          ],
          data: [this.gb(this.total - this.free), this.gb(this.free)]
        }
      ]
    }, {responsive: true, maintainAspectRatio: true})
  },
  methods: {
    gb: x => x/1024/1024/1024
  }
}
</script>
