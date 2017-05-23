import React, {Component} from 'react';
import {Button, Intent, Tree} from "@blueprintjs/core";
import logo from './logo.svg';
import './App.css';
import 'whatwg-fetch';
import Promise from 'promise-polyfill';

if (!window.Promise) {
    window.Promise = Promise;
}

class App extends Component {
    constructor() {
        super();
        this.state = {
            directories: [],
            treeNodes: [],
        }
    }

    getDirectories() {
        const directories = this.state.directories;
        fetch('http://localhost:8080/directory/list')
            .then(function (response) {
                return response.json()
            }).then(function (parsedResponse) {
                console.log(parsedResponse)
        })
    }

    render() {
        return (
            <div className="App">
                <div className="App-header">
                    <img src={logo} className="App-logo" alt="logo"/>
                    <h2>Welcome to React</h2>
                </div>
                <p className="App-intro">
                    To get started, edit <code>src/App.js</code> and save to reload.
                    test
                </p>
                <div>
                    <Button iconName="refresh" text="Refresh" intent={Intent.DANGER}
                            onClick={() => this.getDirectories() }/>
                </div>
                <div>
                    <table className="pt-table .modifier">
                        <thead>
                        <tr>
                            <th>Project</th>
                            <th>Description</th>
                            <th>Technologies</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>Blueprint</td>
                            <td>CSS framework and UI toolkit</td>
                            <td>Sass, TypeScript, React</td>
                        </tr>
                        <tr>
                            <td>TSLint</td>
                            <td>Static analysis linter for TypeScript</td>
                            <td>TypeScript</td>
                        </tr>
                        <tr>
                            <td>Plottable</td>
                            <td>Composable charting library built on top of D3</td>
                            <td>SVG, TypeScript, D3</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        );
    }
}

export default App;
