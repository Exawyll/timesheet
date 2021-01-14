import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IWeek, Week } from 'app/shared/model/week.model';
import { WeekService } from './week.service';
import { WeekComponent } from './week.component';
import { WeekDetailComponent } from './week-detail.component';
import { WeekUpdateComponent } from './week-update.component';

@Injectable({ providedIn: 'root' })
export class WeekResolve implements Resolve<IWeek> {
  constructor(private service: WeekService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IWeek> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((week: HttpResponse<Week>) => {
          if (week.body) {
            return of(week.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Week());
  }
}

export const weekRoute: Routes = [
  {
    path: '',
    component: WeekComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Weeks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: WeekDetailComponent,
    resolve: {
      week: WeekResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Weeks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: WeekUpdateComponent,
    resolve: {
      week: WeekResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Weeks',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: WeekUpdateComponent,
    resolve: {
      week: WeekResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'Weeks',
    },
    canActivate: [UserRouteAccessService],
  },
];
