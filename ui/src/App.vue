<template>

    <sui-container fluid id="app">
        <sui-segment>
            <h1 is="sui-header">{{message}}</h1>

            <transition name="fade">
                <sui-message
                    v-if="infoVisible"
                    info
                    icon="info"
                    header="Choose from the options below"
                    dismissable
                    @dismiss="handleDismiss">
                    <p>
                        Enter an amount for the base currency. Then choose the converted currency option and a date to get
                        the specific conversion rate for that day in time.
                    <br/>
                        This application was built for demo purposes to put on display the features of docker containers, spring-cloud
                        and micro-service architecture.
                    </p>
                </sui-message>
            </transition>

            <div is="sui-divider" horizontal>
                <h4 is="sui-header">
                    <i class="money bill alternate outline icon"></i>
                    Converter
                </h4>
            </div>

            <sui-container class="spacer">

                <sui-container>
                    <sui-dropdown class="spacer-dropdown"
                        :options="months"
                        placeholder="Month"
                        selection
                        v-model="currentMonth"
                        @click="convert"  />

                    <sui-dropdown class="spacer-dropdown"
                        :options="days"
                        placeholder="Day"
                        search
                        selection
                        v-model="currentDay"
                        @click="convert" />

                    <sui-dropdown
                        :options="years()"
                        placeholder="Year"
                        search
                        selection
                        v-model="currentYear"
                        @click="convert" />
                </sui-container>

                <br />

                <sui-container fluid>
                    <sui-input placeholder="Base Amount" class="amount-input spacer-dropdown" v-model="currentAmount" @keyup="convert" />
                    <sui-dropdown class="curr"
                        @keyup="convert"
                        @click="convert"
                        placeholder="--"
                        selection
                        v-model="currentBaseCurrency"
                        :options="currencies" />
                </sui-container>

                <h4 is="sui-header">- TO -</h4>

                <sui-container>
                    <sui-input placeholder="Converted Amount" class="amount-input spacer-dropdown" v-model="currentConvertedAmount" disabled />
                    <sui-dropdown class="curr"
                        @keyup="convert"
                        @click="convert"
                        placeholder="--"
                        selection
                        v-model="currentConvertedCurrency"
                        :options="currencies" />
                </sui-container>

            </sui-container>

            <div is="sui-divider" horizontal>
                <h4 is="sui-header">
                    <i class="check icon"></i>
                    Result
                </h4>
            </div>

            <sui-container class="spacer">
                <h1>
                    <i
                        v-if="icon() !== null"
                        :class="icon(false)">
                    </i>
                    <span v-if="icon().indexOf('sign') < 0">
                        {{ currentConvertedCurrency }}
                    </span>
                    <span>{{ formatNumber(currentConvertedAmount) }}</span>
                </h1>
            </sui-container>

        </sui-segment>
      </sui-container>

</template>

<script>

export default {
  name: 'app',
  data() {
    return {
        infoVisible: true,
        message: 'Super Awesome Currency Converter!',
        currentAmount: null,
        currentConvertedAmount: null,
        currentMonth: new Date().getMonth() + 1,
        months: [
            {key: 1, value: 1, text: 'January'},
            {key: 2, value: 2, text: 'February'},
            {key: 3, value: 3, text: 'March'},
            {key: 4, value: 4, text: 'April'},
            {key: 5, value: 5, text: 'May'},
            {key: 6, value: 6, text: 'June'},
            {key: 7, value: 7, text: 'July'},
            {key: 8, value: 8, text: 'August'},
            {key: 9, value: 9, text: 'September'},
            {key: 10, value: 10, text: 'October'},
            {key: 11, value: 11, text: 'November'},
            {key: 12, value: 12, text: 'December'}
        ],
        currentDay: new Date().getDate(),
        days: Array.from({length: 31}, (x,i) => {return {key: i+1, value: i+1, text: (i+1).toString()}}),
        currentYear: new Date().getFullYear(),
        currentBaseCurrency: 'USD',
        currentConvertedCurrency: 'USD',
        currencies: ['USD'],
        currencyIcons: {
            USD: "dollar sign icon",
            EUR: "euro sign icon",
            MTL: "lira sign icon",
            GBP: "pound sign icon",
            BYR: "ruble sign icon",
            INR: "rupee sign icon",
            MUR: "rupee sign icon",
            NPR: "rupee sign icon",
            PKR: "rupee sign icon",
            SCR: "rupee sign icon",
            LKR: "rupee sign icon",
            ILS: "shekel sign icon",
            KRW: "won sign icon",
            KPW: "won sign icon",
            JPY: "yen sign icon"
        }
    }
  },
  methods: {
    handleDismiss() {
        this.infoVisible = false
    },
    years() {
        var currYear = new Date().getFullYear();
        var startYear = 1980;
        var years = []
        while (startYear <= currYear) {
            var inst = startYear++
            years.push({key: inst, value: inst, text: inst.toString()});
        }
        return years.sort((a,b) => a-b);
    },
    convert() {
        let dt = `${this.currentYear}-${this.currentMonth < 10 ? '0' + this.currentMonth : this.currentMonth}-${this.currentDay < 10 ? '0' + this.currentDay : this.currentDay}`
        fetch(`/currency-converter/specific/${this.currentConvertedCurrency}/${this.currentAmount}/${this.currentBaseCurrency}/${dt}`)
            .then(resp => resp.json())
            .then(data => {
                this.currentConvertedAmount = data.toAmount;
            });
    },
    icon() {
        return this.currencyIcons[this.currentConvertedCurrency] || this.currentConvertedCurrency
    },
    formatNumber(num) {
        if (!num) return null;
        return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
    }
  },
  created() {
        fetch('/exchange-rates/today')
            .then(resp => resp.json())
            .then(data => {
                var curr = [];
                Object.keys(data.rates).forEach(c => {curr.push({key: c, value: c, text: c})});
                curr.sort();
                this.currencies = curr;
            });

        this.convert()
  }
}

</script>

<style>

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  padding-left: 120px;
  padding-right: 120px;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.35s;
}
.fade-enter, .fade-leave-to {
  opacity: 0;
}

.amount-input {
    min-width: 600px;
}

.spacer-dropdown {
    margin-right: 15px;
}

.curr {
    min-width: 105px;
    text-align: center;
}

.spacer {
    margin-top: 40px;
    margin-bottom: 40px;
}

</style>
