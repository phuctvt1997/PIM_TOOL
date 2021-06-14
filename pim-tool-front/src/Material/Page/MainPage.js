import React, {useState} from 'react';
import {Col, Container, Row} from 'react-bootstrap';
import {BrowserRouter, Route} from 'react-router-dom';
import Header from './Header';
import Navigation from './Navigation';
import ErrorScreen from './ErrorScreen';
import AddNewProject from "./AddNewProject";
import ProjectList from "./ProjectList";
import EditAProject from "./EditAProject";

import en from '../lang/en';
import vi from '../lang/vi';
import counterpart from "counterpart";

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('vi', vi);

export const GlobalStateContext = React.createContext({
    language: 'en',
    SetLanguage: () => {}
});
const MainPage = () => {
    const defaultLanguage = sessionStorage.getItem("lang") ? sessionStorage.getItem("lang") : "en";
    const [language, SetLanguage] = useState(defaultLanguage);
    const passedValue = {language, SetLanguage}

    return (
        <GlobalStateContext.Provider value={passedValue}>
                <div className="main">
                    <Container fluid>
                        <Row>
                            <Col>
                                <Header/>
                            </Col>
                        </Row>
                        <BrowserRouter>
                            <Row>
                                <Col xl={1}/>
                                <Col xl={2}>
                                    <Route path="/" component={Navigation}/>
                                </Col>
                                <Col xl={9}>
                                    <Route exact path="/" component={ProjectList}/>
                                    <Route exact path="/NEW" component={AddNewProject}/>
                                    <Route exact path="/edit" component={EditAProject}/>
                                    <Route path="/error" component={ErrorScreen}/>
                                </Col>
                                <Col>

                                </Col>
                            </Row>
                        </BrowserRouter>
                    </Container>
                </div>
        </GlobalStateContext.Provider>
    )
}

export default MainPage;