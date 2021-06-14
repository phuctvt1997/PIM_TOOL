import React, {useContext} from 'react';
import Translate from 'react-translate-component';
import {Col, Container, Row} from 'react-bootstrap';
import '../Style/Header.css';
import logo from '../Images/logo_elca.png';
import counterpart from 'counterpart';
import en from '../lang/en';
import vi from '../lang/vi';
import {GlobalStateContext} from './MainPage';

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('vi', vi);


const Header = () => {
    const {language, SetLanguage} = useContext(GlobalStateContext);

    const onLangChange = function (lang) {
        counterpart.setLocale(lang);
        SetLanguage(lang)
        sessionStorage.setItem('lang',lang);
        console.log(language)
    }
    return (
        <div className="content">
            <Container fluid>
                <Row>
                    <Col xl={1}>
                    </Col>
                    <Col xl={1}>
                        <img className="logo" src={logo} alt="logo"/>
                    </Col>
                    <Col xl={6} >
                        <p className="name">
                            <Translate content="header.name"/>
                        </p>
                    </Col>
                    <Col xl={4}>
                        <p className={"Multilingual"}>
                            <a onClick={() =>
                            onLangChange('en')}><img  className={"flagIcon"} src={"https://image.flaticon.com/icons/png/512/555/555417.png"}/>
                            </a>|
                            <a onClick={() =>
                                onLangChange('vi')}><img className={"flagIcon"} src={"https://lh3.googleusercontent.com/proxy/CMhvhg_eRLZkgEetfsHVOzoKNrGkAMR2LAokModerXACx_2V9m1AsHaqt_YAko9mo_Be3yaa2sBzRA"}/>
                            </a>
                            <img  className={"dragon"} src={"https://i.pinimg.com/originals/b1/52/60/b152608f71618ec20447931d040ec328.gif"}/>
                        </p>

                    </Col>

                </Row>
            </Container>
        </div>
    )
}

export default Header