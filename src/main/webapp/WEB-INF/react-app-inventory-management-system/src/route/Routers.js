import React from 'react'
import {
    BrowserRouter as Router,
    Switch,
    Route
} from 'react-router-dom'
import { Container } from 'react-bootstrap'
import Header from '../components/navigation/Header'
import Home from '../pages/home/Home'
import Product from '../pages/products/Products'
import Register from '../pages/register/Register'
import Login from '../pages/login/Login'
import About from '../pages/about/About'

export default function Routers() {
    return (
        <Router>
            <Header />
            <Container fluid={true}>
                <Switch>
                    <Route exact path="/" component={Home} />
                    <Route path="/product" component={Product} />
                    <Route path="/register" component={Register} />
                    <Route path="/login" component={Login} />
                    <Route path="/about" component={About} />
                </Switch>
            </Container>
        </Router>
    )
}