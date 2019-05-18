import React from 'react';
import './App.css';

class App extends React.Component {

  constructor(props){
    super(props);

    this.state = {
      hour: 1,
      minutes: 0,
      meridiem: "AM",
      loading: false,
      results: []
    }

    this.hours = []
    this.minutes = []
  }

  componentWillMount() {
    for(var i = 1; i <= 12; i++)
      this.hours.push(i)

    for(var j = 0; j <= 60; j += 5)
      this.minutes.push(j)
  }

  onHourChange = (event) => {
      this.setState({hour: event.target.value});
  }

  onMinutesChange = (event) => {
      this.setState({minutes: event.target.value});
  }

  search = () => {
    var url = 'http://localhost:8080/flights/search';
    const params = "hour=" + this.state.hour + "&minutes=" + this.state.minutes + "&meridiem=" + this.state.meridiem;

    this.setState({ loading: true })

    setTimeout(() => {
      fetch(url + "?" + params)
      .then(response => response.json())
      .then(data => {
        this.setState({
          results: data.flights || [],
          loading: false
        })
      })
      .catch(error => {
        this.setState({ loading: false })
        alert('Failed to search for flights. Make sure the server is availabe on ' + url)
        console.log(error)
      });
    }, 1500);
  }

  render() {
    const hoursItems = this.hours.map((hour) =>
      <option key={hour} value={hour}>{hour}</option>
    );

    const minutesItems = this.minutes.map((min) =>
      <option key={min} value={min}>{min >= 10 ? min : "0" + min}</option>
    );

    const resultsItems = this.state.results.map((flight, i) =>
      <div key={i} className="card">
        <div className="content">
          <div className="header">{'Flight #' + (i + 1)}</div>
          <div className="meta">
            {flight.flight}
          </div>
          <div className="description">
            {flight.departure}
          </div>
        </div>
      </div>
    )

    return (
      <div className="App">
        <header className="App-header">
          <img
            src={"https://www.expedia.ca/_dms/header/logo.svg?locale=fr_CA&siteid=4&test=b2p-yellow-header"}
            className="App-logo" alt="logo" />
          <div className="App-header-title">Java Coding Test</div>
        </header>
        <div className="ui container">
          <h2 className="ui icon header">
            <i className="plane icon"></i>
            <div className="content">
              Flight Search
              <div className="sub header">To search a flight you simply enter the time of the departure.</div>
            </div>
          </h2>

          <div className="ui form">
            <h4 className="ui dividing header">Flight Information</h4>
              <div className="fields">
                <div className="two wide field">
                  <label>Hour:</label>
                  <select
                    className="ui fluid search dropdown"
                    name="hour"
                    value={this.state.hour}
                    onChange={this.onHourChange}>
                    {hoursItems}
                  </select>
                </div>
                <div className="two wide field">
                  <label>minutes:</label>
                  <select
                    className="ui fluid search dropdown"
                    name="minutes"
                    value={this.state.minutes}
                    onChange={this.onMinutesChange}>
                    {minutesItems}
                  </select>
                </div>
                <div className="two wide field">
                  <label>-</label>
                  <select
                    className="ui fluid search dropdown"
                    name=""
                    value={this.state.meridiem}
                    onChange={(e) => { this.setState({ meridiem: e.target.value })}}>
                    <option value="AM">AM</option>
                    <option value="PM">PM</option>
                  </select>
                </div>
                <div className="two wide field">
                  <label>-</label>
                  <button className="ui primary button" onClick={this.search}>
                    <i className="plane icon"></i>
                    Search
                  </button>
                </div>
              </div>
          </div>

          <div className="search-results">
            <h4 className="ui dividing header">Search Results</h4>

            {
              this.state.loading &&
              <div>
                <div className="ui active inverted dimmer">
                  <div className="ui text loader">Loading</div>
                </div>
              </div>
            }

            <div className="ui cards">
              {resultsItems}
            </div>
          </div>

        </div>
      </div>
    );
  }
}

export default App;
