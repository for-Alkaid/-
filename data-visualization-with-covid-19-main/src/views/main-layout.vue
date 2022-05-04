<template>
  <div class="container">
    <!-- 顶部 -->
    <div class="top-header">
      <div class="top-header-left">
        <router-link to="/zero-patient"><el-button type="info" round>零号病人</el-button></router-link>&nbsp;&nbsp;
        <router-link to="/covid19-graph-mock"><el-button type="info" round>疫情扩散模拟</el-button> </router-link>
      </div>
      <div class="title">
        <h1>{{ title }}</h1>
        <div class="top-header-tip">
          <div class="sub-title">此数据为实时真实数据，数据来源：腾讯新闻</div>
          <div class="last-update-time">
            更新时间：{{ basicData.updateTime }}
          </div>
        </div>
      </div>
    </div>
    <div class="main-content">
      <el-row>
        <el-col :span="18">
          <!-- 中间信息开始 -->
          <div class="statistics-content">
            <!-- 中间左侧开始 -->
            <div class="main-inner">
              <el-row>
                <el-col :span="7">
                  <chart-card
                    title="累计排名（TOP 10）"
                    :customClass="`chart-item-bottom-sep`"
                  >
                    <province-ranking-bar-chart
                      ref="topConfirmedCountRankChart"
                      :data="top10ProvinceData"
                      style="width: 100%; height: 380px"
                    />
                  </chart-card>
                  <!-- 占比 -->
                  <chart-card
                    title="占比"
                    :customClass="`chart-item-bottom-sep`"
                  >
                    <basic-proportion-chart
                      ref="basicProportionChart"
                      :data="basicData"
                      style="width: 100%; height: 120px"
                    />
                  </chart-card>
                  <chart-card title="最近一周累计治愈">
                    <current-confirmed-compare-bar-chart
                      ref="confirmSingleBarChart"
                      :data="confirmSingleBarChartData"
                      style="width: 100%; height: 310px"
                    />
                  </chart-card>
                </el-col>
                <el-col :span="17">
                  <!-- 顶部基本统计信息开始 -->
                  <div class="basic-header flex">
                    <!-- 顶部统计信息开始 -->
                    <div class="top-basic-info">
                      <basic-data-item-label
                        label="现有确诊"
                        :config="defaultDataConfig.currentConfirmedCount"
                        :inCrValue="basicData.chinaAdd.nowConfirm"
                      />
                      <basic-data-item-label
                        label="累计确诊"
                        :config="defaultDataConfig.confirmedCount"
                        :inCrValue="basicData.chinaAdd.confirm"
                      />
                      <!-- 境外输入 -->
                      <basic-data-item-label
                        label="境外输入"
                        :config="defaultDataConfig.importedCount"
                        :inCrValue="basicData.chinaAdd.importedCase"
                      />
                      <!-- 无症状感染者 -->
                      <basic-data-item-label
                        label="无症状感染者"
                        :config="defaultDataConfig.noInFectCount"
                        :inCrValue="basicData.chinaAdd.noInfect"
                      />
                      <!-- 累计治愈 -->
                      <basic-data-item-label
                        label="累计治愈"
                        :config="defaultDataConfig.curedCount"
                        :inCrValue="basicData.chinaAdd.heal"
                      />
                      <!-- 死亡人数 -->
                      <basic-data-item-label
                        label="累计死亡"
                        :config="defaultDataConfig.deadCount"
                        :inCrValue="basicData.chinaAdd.dead"
                      />
                    </div>
                    <!-- 顶部统计信息结束 -->
                  </div>
                  <!-- 顶部基本统计信息开始 -->
                  <div class="main-inner-map-wraper">
                    <!-- 地图 -->
                    <div class="map">
                      <data-map
                        ref="dataMap"
                        title=""
                        :list="mapDataList"
                        style="width: 100%; height: 100%"
                      />
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
            <!-- 中间左侧结束 -->
          </div>
          <!-- 中间信息结束 -->
        </el-col>
        <br><br><br><br><br><br>
        <el-col :span="6">
          <!-- 右侧区域开始 -->
          <div class="main-right">
            <!--治愈率和死亡率 -->
            <chart-card
              title="治愈率和死亡率"
              :innerClass="`cure-and-dead-rate-chart`"
              :customClass="`chart-item-bottom-sep`">
              <cured-and-dead-rate-chart
                ref="cureRateChart"
                :data="rate"
                title="治愈率"
                style="width: 280px; height: 130px"
              />
            </chart-card>
            <chart-card title="新增趋势" :customClass="chart-item-bottom-sep">
              <basic-trend-chart
                :data="basicIncrTrendData"
                ref="confirmedCountTrendChart"
                style="width: 100%; height: 300px"
              />
            </chart-card>
            <chart-card title="">
              <div slot="title" class="province-table-title flex">
                各省累计确诊
                <el-link
                  icon="el-icon-view"
                  style="color: #bcbcbf; padding-left: 10px"
                  :underline="false"
                  @click="provinceTableDialogShowHandler"
                  >详情</el-link
                >
              </div>
               <dv-scroll-board
                  :config="provinceConfirmedCountBoardConfig"
                  style="width: 100%; height: 360px"
                />
            </chart-card>
          </div>
          <!-- 右侧区域结束 -->
        </el-col>
      </el-row>
    </div>
    <!-- 其他页面 -->
    <div class="province-data-table-wrapper">
      <el-dialog
        :visible.sync="provinceTableDialogVisible"
        width="50%"
        :before-close="provinceTableDialogClose"
      >
        <div slot="title" class="province-data-modal-title">
          <p>各省数据表</p>
          <span class="province-data-modal-update-time"
            >（更新时间：{{ basicData.updateTime }}）
          </span>
        </div>
        <div class="area-data-table-wrapper">
          <el-table
            class="area-data-table"
            :data="provinceDataList"
            style="width: 100%"
          >
            <el-table-column prop="name" align="center" label="省份"></el-table-column>
            <el-table-column
              prop="total.confirm"
              align="center"
              label="累计确诊"
            >
            </el-table-column>
            <el-table-column
              prop="total.nowConfirm"
              align="center"
              label="现有确诊"
            >
            </el-table-column>
            <el-table-column prop="total.heal" align="center" label="累计治愈"></el-table-column>
            <el-table-column prop="total.dead" align="center" label="累计死亡"></el-table-column>
          </el-table>
        </div>
      </el-dialog>
      <!-- 关于弹窗 -->
      <el-dialog
        title="关于"
        :visible.sync="aboutDialogVisible"
        width="30%"
        :before-close="aboutDialogClose"
      >
        <about />
      </el-dialog>
      <!-- 关于图标 -->
      <div class="about-wraper">
        <i
          class="el-icon-info"
          style="font-size: 30px"
          @click="aboutDialogShowHandler"
        ></i>
      </div>
    </div>
  </div>
</template>
<script>
import ChartCard from '../components/ChartCard'
import DataMap from '../components/DataMap'
import CuredAndDeadRateChart from '../components/CuredAndDeadRateChart'
import BasicDataItemLabel from '../components/BasicDataItemLabel'
import BasicTrendChart from '../components/BasicTrendChart'
import ProvinceRankingBarChart from '../components/ProvinceRankingBarChart'
import CurrentConfirmedCompareBarChart from '../components/CurrentConfirmedCompareBarChart'
import About from '../components/About'
import BasicProportionChart from '../components/BasicProportionChart'

import covid19Service from '../api/covid19'

const formatter = (number) => {
  const numbers = number.toString().split('').reverse()
  const segs = []
  while (numbers.length) segs.push(numbers.splice(0, 3).join(''))
  return segs.join(',').split('').reverse().join('')
}
// 数据样式
const getNumberStyle = (color = '#E8EAF6', fontSize = 30, fontWeight = 'bolder') => {
  return {
    fontSize: fontSize,
    fill: color,
    fontWeight: fontWeight
  }
}

const initBasicConfig = (data = null) => {
  let currentConfirmedCount = data ? [data.chinaTotal.nowConfirm] : 0
  let confirmedCount = data ? [data.chinaTotal.confirm] : 0
  let importedCount = data ? [data.chinaTotal.importedCase] : 0
  let noInFectCount = data ? [data.chinaTotal.noInfect] : 0
  let deadCount = data ? [data.chinaTotal.deadCount] : 0
  let curedCount = data ? [data.chinaTotal.heal] : 0
  return {
    confirmedCount: {
      number: [confirmedCount],
      content: '{nt}',
      formatter,
      style: getNumberStyle()
    },
    currentConfirmedCount: {
      number: [currentConfirmedCount],
      content: '{nt}',
      formatter,
      style: getNumberStyle('#2E8EEA')
    },
    importedCount: {
      number: [importedCount],
      content: '{nt}',
      formatter,
      style: getNumberStyle()
    },
    noInFectCount: {
      number: [noInFectCount],
      content: '{nt}',
      formatter,
      style: getNumberStyle()
    },
    deadCount: {
      number: [deadCount],
      content: '{nt}',
      formatter,
      style: getNumberStyle('#D32E58')
    },
    curedCount: {
      number: [curedCount],
      content: '{nt}',
      formatter,
      style: getNumberStyle()
    }
  }
}

const initProvinceConfirmedCountBoardConfig = (resultList = []) => {
  return {
    header: ['省份', '累计确诊', '累计治愈', '累计死亡'],
    headerHeight: 30,
    data: resultList,
    align: ['center'],
    rowNum: 10,
    index: true,
    indexHeader: '排名',
    headerBGC: '',
    oddRowBGC: '',
    evenRowBGC: '',
    carousel: 'single'
  }
}
export default {
  components: {
    ChartCard,
    DataMap,
    CuredAndDeadRateChart,
    BasicDataItemLabel,
    BasicTrendChart,
    ProvinceRankingBarChart,
    CurrentConfirmedCompareBarChart,
    About,
    BasicProportionChart
  },
  data () {
    return {
      title: '全国新冠肺炎疫情数据大屏',
      provinceTableDialogVisible: false,
      aboutDialogVisible: false,
      commonData: {},
      basicData: {
        chinaTotal: {
          confirm: 0,
          deadCount: 0,
          nowConfirm: 0,
          localConfirm: 0,
          importedCase: 0,
          noInfect: 0,
          heal: 0,
        },
        chinaAdd: {
          confirm: 0,
          nowConfirm: 0,
          dead: 0,
          heal: 0,
          noInfect: 0,
          importedCase: 0,

        },
        // currentConfirmedCount: 0,
        currentConfirmedIncr: 0,
        confirmedCount: 0,
        confirmedIncr: 0,
        curedCount: 0,
        curedIncr: 0,
        deadIncr: 0,
        sure: 0,
        sureAdd: 0,
        importedCount: 0,
        importedIncr: 0,
        noInFectCount: 0,
        noInFectIncr: 0,
        suspectCount: 0,
        suspectIncr: 0,
        updateTime: '-'
      },
      defaultDataConfig: initBasicConfig(),
      cureRateConfig: {
        data: [0],
        shape: 'round'
      },
      deadRateConfig: {
        data: [0],
        shape: 'round'
      },
      countryRankingTakeTurnChartConfig: {
        data: [],
        waitTime: 2000,
        unit: '单位',
        valueFormatter ({ value }) {
          const reverseNumber = (value + '').split('').reverse()
          let valueStr = ''

          while (reverseNumber.length) {
            const seg = reverseNumber.splice(0, 3).join('')
            valueStr += seg
            if (seg.length === 3) valueStr += ','
          }

          return valueStr.split('').reverse().join('')
        }
      },
      provinceConfirmedCountBoardConfig: initProvinceConfirmedCountBoardConfig(),
      provinceDataList: [],
      trendDataList: [],
      confirmedCountList: [],
      top10ProvinceData: {
        provinceList: [],
        valueList: []
      },
      basicIncrTrendData: {
        dateList: [],
        importedIncrDataList: [],
        currentConfirmedIncrDataList: []
      },
      confirmSingleBarChartData: {
        dateList: [],
        currentConfirmedCountList: [],
        confirmedCountList: []
      },
      rate: {
        curedRate: 0,
        deadRate: 0
      },
      areaData: {},
      mapDataList: []
    }
  },
  methods: {

    queryBasicData () {
      let self = this
      covid19Service.getOverall().then((res) => {
        if (!res) {
          console.log('错误:' + res)
          return
        }
        // console.info(res)
        self.basicData = res
        self.setBasicData(res)
        // region 设置省数据
        // console.info(res.areaList[0].areaChild)
        self.provinceDataList = res.areaList[0].areaChild
        self.setAreaChartData(res.areaList[0].areaChild)
        // 设置累计排名数据
        self.setProvinceRankingData(res.areaList[0].areaChild)
        // 设置各省累计确诊轮播表格数据
        self.setProvinceComfirmedCountBoardData(res.areaList[0].areaChild)
        // // 设置地图数据
        self.setMapData(res.areaList[0].areaChild)
        // region end
      })
    },
    // queryProvinceDataList () {
    //   let self = this
    //   covid19Service.getProvinceDataList().then((res) => {
    //     if (!res.success) {
    //       // TODO 错误处理...
    //       console.log('错误:' + res.info)
    //       return
    //     }
    //     self.provinceDataList = res.data
    //     self.setAreaChartData(res.data)
    //     // 设置累计排名数据
    //     self.setProvinceRankingData(res.data)
    //     // 设置各省累计确诊轮播表格数据
    //     self.setProvinceComfirmedCountBoardData(res.data)
    //     // 设置地图数据
    //     self.setMapData(res.data)
    //   })
    // },
    queryTrendDataList () {
      let self = this
      covid19Service.getDailyList().then((res) => {
        if (!res) {
          // TODO 错误处理...
          console.log('错误:' + res)
          return
        }
        self.setRate(res)
        self.trendDataList = res
        // 重置图表数据
        self.setBasicIncrTrendData(res)
      })
    },
    setProvinceRankingData (areaList) {
      let provinceList = []
      let dataValueList = []
      for (let i = 0; i < 10; i++) {
        provinceList.push(areaList[i].name)
        dataValueList.push(areaList[i].total.nowConfirm)
      }
      let data = {
        provinceList: provinceList,
        valueList: dataValueList
      }
      this.top10ProvinceData = data
    },
    setBasicIncrTrendData (data) {
      let dateList = []
      let currentConfirmedIncrList = []
      let importedIncrList = []

      let sevenDayDateList = []
      // 仅显示一周条数据
      let confirmedCountList = []
      let curedCountList = []
      // 仅获取7条数据
      let count = 7
      let noInFectDataList = []

      for (let i = 0; i <= data.chinaDayList.length - 1; i++) {
        dateList.push(data.chinaDayAddList[i].date)
        currentConfirmedIncrList.push(data.chinaDayAddList[i].localConfirmadd)
        importedIncrList.push(data.chinaDayAddList[i].importedCase)
        noInFectDataList.push(data.chinaDayAddList[i].infect)
      }
      // for (let i = data.importedIncrList.length - 1; i >= 0; i--) {
      // }
      // for (let i = data.noInFectCountList.length - 1; i >= 0; i--) {
      // }

      for (let i = count; i >= 0; i--) {
        if (confirmedCountList.length >= count) {
          break
        }
        sevenDayDateList.push(data.chinaDayList[data.chinaDayList.length-1-i].date)
        confirmedCountList.push(data.chinaDayList[data.chinaDayList.length-1-i].nowConfirm)
      }
      for (let i = count; i >= 0; i--) {
        if (curedCountList.length >= count) {
          break
        }
        curedCountList.push(data.chinaDayList[data.chinaDayList.length-1-i].heal)
      }


      this.basicIncrTrendData = {
        dateList: dateList,
        importedIncrDataList: importedIncrList,
        currentConfirmedIncrDataList: currentConfirmedIncrList,
        noInFectDataList: noInFectDataList
      }
      this.confirmSingleBarChartData = {
        dateList: sevenDayDateList,
        curedCountList: curedCountList,
        confirmedCountList: confirmedCountList
      }
    },
    setProvinceComfirmedCountBoardData (areaList) {
      let resultList = areaList.map(item => {
        return [item.name, item.total.confirm, item.total.heal, item.total.dead]
      })
      // 重新生成，否则无法刷新状态
      this.provinceConfirmedCountBoardConfig = initProvinceConfirmedCountBoardConfig(resultList)
    },
    setAreaChartData (areaList) {
      let confirmedCountList = []
      let provinceList = []
      let curedCountList = []
      let deadCountList = []
      areaList.forEach(item => {
        provinceList.push(item.name)
        confirmedCountList.push(item.total.confirm)
        curedCountList.push(item.total.heal)
        deadCountList.push(item.total.dead)
      })
      this.areaData = {
        provinceList: provinceList,
        confirmedCountList: confirmedCountList,
        curedCountList: curedCountList,
        deadCountList: deadCountList
      }
      // console.info(this.areaData)
    },
    setMapData (dataList) {
      let list = dataList.map(item => {
        return {
          name: item.name,
          value: item.total.nowConfirm
        }
      })
      this.mapDataList = list
    },
    provinceTableDialogShowHandler () {
      this.provinceTableDialogVisible = true
    },
    provinceTableDialogClose () {
      this.provinceTableDialogVisible = false
    },
    aboutDialogShowHandler () {
      this.aboutDialogVisible = true
    },
    aboutDialogClose () {
      this.aboutDialogVisible = false
    },
    setBasicData (data) {
      // 重新生成，否则视图不更新
      let config = initBasicConfig(data)
      this.defaultDataConfig = config
    },
    setRate(data) {

      // 处理治愈率和死亡率
      this.rate = {
        curedRate: data.chinaDayList[data.chinaDayList.length-1].healRate / 100,
        deadRate: data.chinaDayList[data.chinaDayList.length-1].deadRate / 100
      }
      console.info(this.rate)
    },
    startQueryData () {
      this.queryBasicData()
      // this.queryProvinceDataList()
      this.queryTrendDataList()
    },
    initAllChart() {
      this.$refs.dataMap.initChart()
      this.$refs.cureRateChart.initChart()
      this.$refs.confirmedCountTrendChart.initChart()
      this.$refs.topConfirmedCountRankChart.initChart()
      this.$refs.confirmSingleBarChart.initChart()
      this.$refs.basicProportionChart.initChart()
    }
  },
  mounted () {
    this.initAllChart()
    this.startQueryData()

    let self = this
    // 定义定时器，隔 5 分钟刷新一次
    this.timer = setInterval(() => {
      self.startQueryData()
    }, 1000*60*5)
  },
  beforeDestroy() {
    clearInterval(this.timer)
  }
}
</script>
<style>
.container {
  position: relative;
}
h1 {
  font-size: 35px;
  font-weight: bold;
  padding: 20px;
}
.flex {
  display: flex;
}
.top-header {
  position: relative;
  margin-bottom: 10px;
}
.top-header-left{
  font-size: 18px;
  color: white;
  position: absolute;
  padding: 20px 20px;
  text-align: right;
  left: 0;
}
.top-header-tip {
  font-size: 14px;
  position: absolute;
  padding: 20px 20px;
  text-align: right;
  top: 0;
  right: 0;
}
.title {
  min-width: 350px;
}
.chart-card {
  background: #0f142b;
  border-radius: 10px;
  margin: 0 20px;
}
.main-inner-map-wraper {
  background: #0f142b;
  height: 100%;
  margin: 0 20px;
}
.map {
  width: 99%;
  height: 840px;
  padding: 20px 10px 10px 10px;
}
.province-scroll-board-wrapper {
  padding-top: 10px;
  padding-bottom: 10px;
}
.chart-item-bottom-sep {
  margin-bottom: 20px;
}
.province-table-title {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  padding: 10px 10px 10px 20px;
  text-align: left;
}
.basic-incr-trend-chart-wrapper {
  padding-bottom: 10px;
}
.sub-title,
.last-update-time,
.province-scroll-board-wrapper ::v-deep .dv-scroll-board .header,
.province-scroll-board-wrapper ::v-deep .dv-scroll-board .rows {
  font-size: 14px;
}
.province-scroll-board-wrapper ::v-deep .dv-scroll-board .rows {
  color: #bcbcbf;
}
.province-data-modal-title {
  color: #000;
  font-size: 20px;
  font-weight: bold;
}
.province-data-modal-update-time {
  color: #58585a;
  font-size: 16px;
  font-weight: normal;
  padding-top: 10px;
  display: block;
}
.cure-and-dead-rate-chart {
  display: flex;
  justify-content: space-around;
}
.top-basic-info {
  display: flex;
}
.dv-scroll-ranking-board .ranking-column .inside-column {
  background: linear-gradient(90deg, #29bfff, #a231ff, #0deccd, #29bfff);
}
.chart-item-container {
  margin: 0 10px;
}
.about-wraper {
  position: fixed;
  bottom: 20px;
  right: 20px;
}
a{
  text-decoration: none;
}
.router-link-active {
  text-decoration: none;
}
</style>