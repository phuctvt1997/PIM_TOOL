export default {
    header: {
        name: 'Project Information Management',
    },
    navigation: {
        title: 'Project list',
        new: 'New',
        project: 'Project',
        customer: 'Customer',
        supplier: 'Supplier'
    },
    errorScreen: {
        unexpected: 'Unexpected error occurred',
        please: 'Please',
        contact: 'contact your administrator',
        or: 'or',
        back: 'back to search project'
    },
    headerProject: {
        newProject: 'New Projects',
        editProject: 'Edit Project information',
        projectList: 'Project List'
    },
    addNewProject:{
        projectNumber: "Project Number:",
        projectName: "Project Name:",
        customer: "Customer:",
        group : "Group:",
        member: "Member",
        status: "Status",
        startDate: "Start date",
        endDate : "End date"
    },
    error:{
        concurrentUpdate: "Concurrent update appeared, please refresh",
        projectNumberDuplicated:"Project Number #### is already exist",
        projectNumberConcurrentUpdate:" Project Number #### has already been updated before,please refresh",
        projectEndDate: "End date has to be behind the start date",
        mandatoryField: "Please enter all the mandatory fields (*)",
        inputWrongTime: "End Date has to be after Starting day"
    },
    field:{
        project_name: "Project name:",
        project_number: "Project number:",
        customer: "Customer name:",
        groups: "Groups:",
        member: "Member:",
        status: "Status:",
        start_date: "Start date:",
        end_date: "End date:",
        cancelButton : "Cancel:",
        createButton : "Create Project:",
        saveButton : "Save:",
        searchInputText: "Project Number , name ,Customer Name",
        searchStatusSelection :" select Status",
        searchButton : "Search Button",
        resetSearch : "Reset search",
        deleteIcon : "Delete",
        selectText: "Select"
    },
    invalid:{
        projectNumberInvalid:"is invalid",
        fieldInvalid: "could not be blank"
    },
    statusHandle:{
        NEW:"NEW",
        PLA:"Planned",
        INP:"In Progress",
        FIN:"Finished"
    },
    itemSelect:{
        selected: "item(s) selected",
        deletedSelected:"delete selected items"
    },
    alertConfirm:{
        messageAlert:"ARE YOU SURE ?"
    }
}