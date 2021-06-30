import React, { Component } from 'react'
import { Search } from '../../components/search/Search'

export default class Home extends Component {
    render() {
        return (
            <div>
                <h1>Home</h1>
                <Search />
            </div>
        )
    }
}
