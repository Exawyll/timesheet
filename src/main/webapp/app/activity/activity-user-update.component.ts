import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActivity, Activity } from 'app/shared/model/activity.model';
import { ActivityUserService } from './activity-user.service';
import { IProject } from 'app/shared/model/project.model';
import { ProjectService } from 'app/entities/project/project.service';
import { IUser, User } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IWeek } from 'app/shared/model/week.model';
import { WeekService } from 'app/entities/week/week.service';

type SelectableEntity = IProject | IUser | IWeek;

@Component({
  selector: 'jhi-activity-update',
  templateUrl: './activity-user-update.component.html',
})
export class ActivityUserUpdateComponent implements OnInit {
  isSaving = false;
  projects: IProject[] = [];
  users: IUser[] = [];
  weeks: IWeek[] = [];
  loggedInUser: IUser = {};

  editForm = this.fb.group({
    id: [],
    timeSpent: [],
    projects: [],
    users: [],
    weeks: [],
  });

  constructor(
    protected activityUserService: ActivityUserService,
    protected projectService: ProjectService,
    protected userService: UserService,
    protected weekService: WeekService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ activity }) => {
      this.updateForm(activity);

      this.activityUserService.getUserId().subscribe((user: IUser) => {
        (this.loggedInUser = user),
          this.users.push(this.loggedInUser),
          this.projectService
            .query({ 'userId.equals': user?.id })
            .subscribe((res: HttpResponse<IProject[]>) => (this.projects = res.body || []));
      });

      this.weekService.query().subscribe((res: HttpResponse<IWeek[]>) => (this.weeks = res.body || []));
    });
  }

  updateForm(activity: IActivity): void {
    this.editForm.patchValue({
      id: activity.id,
      timeSpent: activity.timeSpent,
      projects: activity.projects,
      users: activity.users,
      weeks: activity.weeks,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const activity = this.createFromForm();
    if (activity.id !== undefined) {
      this.subscribeToSaveResponse(this.activityUserService.update(activity));
    } else {
      this.subscribeToSaveResponse(this.activityUserService.create(activity));
    }
  }

  private createFromForm(): IActivity {
    return {
      ...new Activity(),
      id: this.editForm.get(['id'])!.value,
      timeSpent: this.editForm.get(['timeSpent'])!.value,
      projects: this.editForm.get(['projects'])!.value,
      users: this.editForm.get(['users'])!.value,
      weeks: this.editForm.get(['weeks'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActivity>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableEntity[], option: SelectableEntity): SelectableEntity {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
