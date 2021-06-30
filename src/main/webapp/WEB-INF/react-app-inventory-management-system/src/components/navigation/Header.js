import React from 'react'
import { Navbar, Nav, Container } from 'react-bootstrap'

export default function Header() {
    return (

        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <Container fluid={true}>
                <Navbar.Brand href="/">Inventory</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="mr-auto" justify>
                        <Nav.Link href="/">Home</Nav.Link>
                        <Nav.Link href="product">Product</Nav.Link>
                        <Nav.Link href="register">Register</Nav.Link>
                        <Nav.Link href="login">Login</Nav.Link>
                        <Nav.Link href="about">About</Nav.Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar >
    )
}
