import React, {Component} from 'react';
import logo from './logo.svg';
import './App.css';
import AppBar from 'material-ui/AppBar';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';

import Paper from 'material-ui/Paper';
import 'whatwg-fetch';

const style = {
	margin: 20
};

class App extends Component {

	constructor(props, context) {
		super(props, context);

		this.state = {
			changed: false,
			tagIdError: null
		}
	}

	componentDidMount = () => {
		fetch('/api/user/current', {credentials: 'same-origin'}).then((response) => {
			return response.json()
		}).then((user) => {
			var state = {
				'displayName': user.displayName,
				'tagId': user.tagId,
				'validTill': user.validTill,
				'email': user.email,
				'phone': user.phone,
				'user': user,
				'open': false
			}
			this.setState(state)
		})
	}

	handleTagIdChange = (event) => {
		this.setState({tagId: event.target.value, changed: true})
	}

	handleTagIdBlur = (event) => {
		var tagId = event.target.value
		var tagIdError = null

		if (tagId === "") {
			tagIdError = "Code is required"
		} else if (!tagId.match(/^\d+$/)) {
			tagIdError = "Code must be number"
		}

		this.setState({tagIdError: tagIdError})

		if (tagIdError === null && this.state.changed) {
			this.state.user.tagId = this.state.tagId
			fetch('/api/user/update', {
				credentials: 'same-origin',
				method: 'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				body: JSON.stringify(this.state.user)
			})
		}
	}

	handleSubmit() {}

	render() {
		var props = {
			fullWidth: true,
			floatingLabelFixed: true
		}

		const {displayName, tagId, validTill, email, phone} = this.state
		const {tagIdError} = this.state

		return <MuiThemeProvider>
			<div>
				<AppBar onLeftIconButtonClick={() => {
						this.setState({open: true})
					}} title={displayName} iconClassNameRight="muidocs-icon-navigation-expand-more"/>
				<Drawer docked={false} width={200} open={this.state.open} onRequestChange={(open) => this.setState({open})}>
					<MenuItem onClick={this.handleClose}>Menu Item</MenuItem>
					<MenuItem onClick={this.handleClose}>Menu Item 2</MenuItem>
				</Drawer>
				<div style={style}>
					<TextField {...props} floatingLabelText="Email" value={email} readOnly={true}/>
					<TextField {...props} floatingLabelText="Phone" value={phone} readOnly={true}/><br/>
					<TextField ref={(input) => {
							this.tagIdInput = input
						}} onBlur={(event) => {
							this.handleTagIdBlur(event)
						}} {...props} name="code" floatingLabelText="Card code" value={tagId} onChange={this.handleTagIdChange} errorText={tagIdError}/><br/>
					<TextField {...props} floatingLabelText="Valid till" value={validTill} readOnly={true}/><br/>
				</div>
			</div>
		</MuiThemeProvider>;

	}
}

export default App;
