import React, {useContext, useEffect} from 'react';
import Translate from 'react-translate-component';
import {Col, Nav, Navbar, Row} from 'react-bootstrap';
import '../Style/Navigation.css';
import counterpart from 'counterpart';
import en from '../lang/en';
import vi from '../lang/vi';
import {GlobalStateContext} from "./MainPage";

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('vi', vi);

const Navigation = () => {
    const {language, SetLanguage} = useContext(GlobalStateContext);

    useEffect(() => {
        console.log(language)
        SetLanguage( counterpart.setLocale(language))
        counterpart.setLocale(language)
    },[])
    return (
        <Navbar collapseOnSelect expand="lg">
            <Navbar.Brand/>
            <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
            <Navbar.Collapse id="responsive-navbar-nav">
                <Nav className="navigation">
                    <Row>
                        <Col xl={12}>
                            <Nav.Link href="/">
                                <p className="text-semi-bold first-element">
                                    <Translate content="navigation.title"/>
                                </p>
                            </Nav.Link>
                            <Nav.Link href={"/NEW"}>
                                <p className="text-semi-bold">
                                    <Translate content="navigation.new"/>
                                </p>
                            </Nav.Link>
                            <Nav.Link><Translate content="navigation.project"/></Nav.Link>
                            <Nav.Link><Translate content="navigation.customer"/></Nav.Link>
                            <Nav.Link><Translate content="navigation.supplier"/></Nav.Link>
                        </Col>
                    </Row>
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    )

}

export default Navigation;