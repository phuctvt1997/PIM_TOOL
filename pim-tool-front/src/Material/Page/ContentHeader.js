import React, {useEffect, useState} from "react";

import counterpart from "counterpart";

import en from '../lang/en';
import vi from '../lang/vi';
import Translate from 'react-translate-component';

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('vi', vi);

const ContentHeader = () => {
    const urlString = window.location.href;
    const [ValueHeader, setValueHeader] = useState('');
    useEffect(() => {
        if (urlString.includes("/edit")) {
            setValueHeader("headerProject.editProject");
        } else if (urlString.includes("/NEW")) {
            setValueHeader("headerProject.newProject");
        } else setValueHeader("headerProject.projectList");
    }, [urlString]);
    return (
        <div className="content-header">
            <Translate component={"h2"} content={ValueHeader}/>
        </div>
    );
}
export default ContentHeader;