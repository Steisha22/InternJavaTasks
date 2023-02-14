import React from 'react';
import PageAccessValidator from 'components/PageAccessValidator';
import EditDishPage from 'pages/EditDish';
import PageContainer from 'components/PageContainer';

const EditDish = () => (
    <PageAccessValidator>
        <PageContainer>
            <EditDishPage />
        </PageContainer>
    </PageAccessValidator>
);

export default EditDish;
