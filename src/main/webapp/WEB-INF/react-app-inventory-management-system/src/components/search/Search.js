import React from 'react'
import { Form, FormControl, Button } from 'react-bootstrap'
export function Search(props) {


    return (
        <>
            <Form inline>
                <FormControl type="text" placeholder="Search" className=" mr-sm-2" />
                <Button type="submit">Submit</Button>
            </Form>
        </>
    )
}
