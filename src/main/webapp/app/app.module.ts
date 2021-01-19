import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { TimesheetSharedModule } from 'app/shared/shared.module';
import { TimesheetCoreModule } from 'app/core/core.module';
import { TimesheetAppRoutingModule } from './app-routing.module';
import { TimesheetHomeModule } from './home/home.module';
import { TimesheetEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';
import { TimesheetActivityUserModule } from './activity/activity-user.module';

@NgModule({
  imports: [
    BrowserModule,
    TimesheetSharedModule,
    TimesheetCoreModule,
    TimesheetHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    TimesheetEntityModule,
    TimesheetActivityUserModule,
    TimesheetAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class TimesheetAppModule {}
